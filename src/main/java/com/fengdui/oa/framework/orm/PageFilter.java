package com.fengdui.oa.framework.orm;

import com.xh.market.framework.util.http.HttpUtil;
import com.xh.market.framework.util.string.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Wander.Zeng
 * @data 2015-8-5 上午10:55:19
 * @desc PageFilter.java
 */
public class PageFilter {

	/** 过滤器前缀 */
	private static final String PREFIX_FILTER = "filter#";
	/** 多个属性间OR关系的分隔符. */
	private final String SEPARATOR_OR = "#OR#";

	/** 字段名 */
	private String propertyName = null;
	/** 比较类型，比如大于等于 */
	private String matchType = null;
	/** 属性值 */
	private Object propertyValue = null;
	/** sql 语句 */
	private String sql = null;

	/**
	 * 构造函数三种写法
	 * 
	 * @param new PageFilter("name = 'test'");
	 * @param new PageFilter("name", "=", "test");
	 * @param new PageFilter("filter#EQ#name", "test");
	 */
	public PageFilter() {

	}

	public PageFilter(String sql) {
		this.sql = sql;
	}

	public PageFilter(String propertyName, String matchType, Object propertyValue) {
		this.propertyName = propertyName;
		this.matchType = matchType;
		this.propertyValue = StringUtil.escapeObj4SQL(propertyValue);
	}

	public PageFilter(final String filterName, final Object value) {
		if (filterName.startsWith(PREFIX_FILTER)) {
			String propertyName = "";// 字段名
			String matchTypeCode = ""; // 比较类型比如EQ
			matchTypeCode = StringUtils.substringBefore(StringUtils.substringAfter(filterName, PREFIX_FILTER), "#");
			propertyName = StringUtils.substringAfter(filterName, matchTypeCode);
			if (StringUtils.isBlank(propertyName) || (propertyName.length() < 2)) {
				throw new IllegalArgumentException("过滤的属性[" + filterName + "]没有按规则编写,无法得到propertyName.");
			}
			matchType = MatchType.getType(matchTypeCode);
			String propertyNameStr = StringUtils.substringAfter(propertyName, "#");
			if (!propertyNameStr.contains(SEPARATOR_OR)) {
				this.propertyName = propertyNameStr;
			} else {
				String[] propertyNames = StringUtils.splitByWholeSeparator(propertyNameStr, SEPARATOR_OR);
				this.sql = "(";
				for (int i = 0; i < propertyNames.length; i++) {
					if (i > 0) {
						this.sql += " or ";
					}
					if (matchType.equals(MatchType.LIKE.value) || matchType.equals(MatchType.ILIKE.value)) {
						this.sql += propertyNames[i] + " " + matchType + " " + "'%" + value + "%'";
					} else {
						this.sql += propertyNames[i] + " " + matchType + " " + StringUtil.escapeObj4SQL(value);
					}
				}
				this.sql += ")";
				return;
			}
			if (matchType.equals(MatchType.LIKE.value) || matchType.equals(MatchType.ILIKE.value)) {
				this.propertyValue = "'%" + value + "%'";
			} else if (matchType.equals(MatchType.IN.value)) {
				if (value instanceof String) {
					if (((String) value).indexOf(",") < 0) {
						this.matchType = MatchType.EQ.value;
						this.propertyValue = StringUtil.escapeObj4SQL(value);
					} else {
						String[] valArray = ((String) value).split(",");
						this.propertyValue = buildPropertyValue4In(valArray);
					}
				} else if (value instanceof String[]) {
					String[] valArray = (String[]) value;
					this.propertyValue = buildPropertyValue4In(valArray);
				}
			} else {
				this.propertyValue = StringUtil.escapeObj4SQL(value);
			}
		} else {
			throw new IllegalArgumentException("过滤的属性[" + propertyName + "]没有按规则编写");
		}
	}

	private String buildPropertyValue4In(String[] valArray) {
		StringBuffer proVal = new StringBuffer();
		proVal.append("(");
		for (int i = 0; i < valArray.length; i++) {
			if (i > 0) {
				proVal.append(", '" + valArray[i] + "'");
			} else {
				proVal.append("'" + valArray[i] + "'");
			}
		}
		proVal.append(")");
		return proVal.toString();
	}

	public static List<PageFilter> buildPageFilters(final HttpServletRequest request) {
		return buildPageFilters(request, PREFIX_FILTER);
	}

	public static List<PageFilter> buildPageFilters(final HttpServletRequest request, final String filterPrefix) {
		List<PageFilter> filterList = new ArrayList<PageFilter>();
		// 从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map
		Map<String, Object> filterParamMap = HttpUtil.getParamMap(request, filterPrefix);
		for (Map.Entry<String, Object> entry : filterParamMap.entrySet()) {
			String filterName = entry.getKey();
			Object valObj = entry.getValue();
			if (valObj instanceof String) {
				String value = (String) valObj;
				if (StringUtils.isNotBlank(value)) {
					PageFilter filter = new PageFilter(filterName, value);
					filterList.add(filter);
				}
			} else if (valObj instanceof String[]) {
				String[] value = (String[]) valObj;
				PageFilter filter = new PageFilter(filterName, value);
				filterList.add(filter);
			}
		}
		return filterList;
	}

	/** 属性比较类型. */
	public enum MatchType {
		// EQ, NE, LIKE, ILIKE, LT, GT, LE, GE, IN, SEQ, SGT, INLONG, ISNULL;
		EQ("="), NE("<>"), LIKE("like"), ILIKE("ilike"), LT("<"), GT(">"), LE("<="), GE(">="), IN("in");

		private String value;

		private MatchType(String value) {
			this.value = value;
		}

		public static String getType(String type) {
			for (MatchType matchType : MatchType.values()) {
				if (matchType.name().equals(type)) {
					return matchType.value;
				}
			}
			return null;
		}
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getMatchType() {
		return matchType;
	}

	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}

	public Object getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(Object propertyValue) {
		this.propertyValue = propertyValue;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

}
