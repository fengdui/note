package com.fengdui.oa.framework.orm;

import com.fengdui.oa.framework.util.reflection.ReflectionUtil;
import com.fengdui.oa.framework.web.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

import javax.xml.bind.PropertyException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author Wander.Zeng
 * @data 2015-8-4 下午6:07:07
 * @desc PagePlugin.java
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class PagePlugin implements Interceptor {

	private static String dialect = ""; // 数据库方言
	private static String matchPoint = ""; // 需要拦截的ID(正则匹配)

	@SuppressWarnings("unchecked")
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		if (invocation.getTarget() instanceof RoutingStatementHandler) {
			RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation.getTarget();
			BaseStatementHandler delegate = (BaseStatementHandler) ReflectionUtil.getFieldValue(statementHandler, "delegate");
			MappedStatement mappedStatement = (MappedStatement) ReflectionUtil.getFieldValue(delegate, "mappedStatement");

			if (mappedStatement.getId().contains(matchPoint)) {
				BoundSql boundSql = delegate.getBoundSql();
				Object parameterObject = boundSql.getParameterObject();// 分页SQL<select>中parameterType属性对应的实体参数，即Mapper接口中执行分页方法的参数,该参数不得为空
				if (null == parameterObject) {
					throw new NullPointerException("parameterObject尚未实例化");
				} else {
					if (parameterObject instanceof Map) {
						Map<String, Object> parameterMap = (Map<String, Object>) parameterObject;
						parameterObject = parameterMap.get("param1");
					}

					Connection connection = (Connection) invocation.getArgs()[0];
					String sql = boundSql.getSql();
					String countSql = "SELECT COUNT(0) FROM (" + sql + ") tmp_count"; // 记录总数
					PreparedStatement countStatement = connection.prepareStatement(countSql);
					BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql, boundSql.getParameterMappings(), parameterObject);
					setParameters(countStatement, mappedStatement, countBS, parameterObject);
					ResultSet rs = countStatement.executeQuery();
					int count = 0;
					if (rs.next()) {
						count = rs.getInt(1);
					}
					rs.close();
					countStatement.close();

					PageInfo pageInfo = null;
					if (parameterObject instanceof PageInfo) {
						// 参数就是Page实体
						pageInfo = (PageInfo) parameterObject;
						pageInfo.initPage(count);
					} else {
						// 参数为某个实体，该实体拥有Page属性
						Field pageInfoField = ReflectionUtil.getAccessibleField(parameterObject, "page");
						if (pageInfoField != null) {
							pageInfo = (PageInfo) ReflectionUtil.getFieldValue(parameterObject, "pageInfo");
							if (null == pageInfo) {
								pageInfo = new PageInfo();
							}
							pageInfo.initPage(count);
							ReflectionUtil.setFieldValue(parameterObject, "pageInfo", pageInfo);
						} else {
							throw new NoSuchFieldException(parameterObject.getClass().getName() + "不存在 pageInfo 属性");
						}
					}
					String pageSql = buildPageSql(sql, pageInfo);
					ReflectionUtil.setFieldValue(boundSql, "sql", pageSql);
				}
			}
		}
		return invocation.proceed();
	}

	/**
	 * 对SQL参数(?)设值
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql, Object parameterObject) throws SQLException {
		ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		if (parameterMappings != null) {
			Configuration configuration = mappedStatement.getConfiguration();
			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
			MetaObject metaObject = parameterObject == null ? null : configuration.newMetaObject(parameterObject);
			for (int i = 0; i < parameterMappings.size(); i++) {
				ParameterMapping parameterMapping = parameterMappings.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					Object value;
					String propertyName = parameterMapping.getProperty();
					PropertyTokenizer prop = new PropertyTokenizer(propertyName);
					if (null == parameterObject) {
						value = null;
					} else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
						value = parameterObject;
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						value = boundSql.getAdditionalParameter(propertyName);
					} else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX) && boundSql.hasAdditionalParameter(prop.getName())) {
						value = boundSql.getAdditionalParameter(prop.getName());
						if (null != value) {
							value = configuration.newMetaObject(value).getValue(propertyName.substring(prop.getName().length()));
						}
					} else {
						value = metaObject == null ? null : metaObject.getValue(propertyName);
					}
					TypeHandler typeHandler = parameterMapping.getTypeHandler();
					if (typeHandler == null) {
						throw new ExecutorException("There was no TypeHandler found for parameter " + propertyName + " of statement " + mappedStatement.getId());
					}
					typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
				}
			}
		}
	}

	/**
	 * 根据数据库方言，生成特定的分页sql
	 */
	private String buildPageSql(String sql, PageInfo pageInfo) {
		if (null != pageInfo && StringUtils.isNotBlank(dialect)) {
			StringBuffer pageSql = new StringBuffer();
			if ("postgresql".equalsIgnoreCase(dialect)) {
				pageSql.append(sql);
				if (null != pageInfo.getOrderBy()) {
					pageSql.append(" ORDER BY " + pageInfo.getOrderBy());
					if (null != pageInfo.getOrder()) {
						pageSql.append(" " + pageInfo.getOrder());
					}
					// pageSql.append(" ORDER BY " + pageInfo.getOrderBy() + " " + null != pageInfo.getOrder() ? pageInfo.getOrder() : "");

					List<String> orderByList = pageInfo.getOrderByList();
					List<String> orderList = pageInfo.getOrderList();
					if (null != orderByList) {
						for (int i = 0; i < orderByList.size(); i++) {
							pageSql.append(", " + orderByList.get(i) + " " + orderList.get(i));
						}
					}
				}
				pageSql.append(" OFFSET " + pageInfo.getCurrentResult() + " LIMIT " + pageInfo.getPageSize());
			}/* else if ("mysql".equalsIgnoreCase(dialect)) {
				pageSql.append(sql);
				pageSql.append(" limit " + pageInfo.getCurrentResult() + "," + pageInfo.getPageSize());
				} else if ("oracle".equals(dialect)) {
				pageSql.append("select * from (select tmp_tb.*,ROWNUM row_id from (");
				pageSql.append(sql);
				pageSql.append(") tmp_tb where ROWNUM<=");
				pageSql.append(pageInfo.getCurrentResult() + pageInfo.getPageSize());
				pageSql.append(") where row_id>");
				pageSql.append(pageInfo.getCurrentResult());
				}*/
			return pageSql.toString();
		} else {
			return sql;
		}
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties p) {
		dialect = p.getProperty("dialect");
		if (StringUtils.isBlank(dialect)) {
			try {
				throw new PropertyException("dialect property is not found!");
			} catch (PropertyException e) {
				e.printStackTrace();
			}
		}
		matchPoint = p.getProperty("matchPoint");
		if (StringUtils.isBlank(matchPoint)) {
			try {
				throw new PropertyException("pageSqlId property is not found!");
			} catch (PropertyException e) {
				e.printStackTrace();
			}
		}
	}

}
