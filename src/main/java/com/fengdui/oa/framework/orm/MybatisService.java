package com.fengdui.oa.framework.orm;

import com.fengdui.oa.framework.constant.ConstantColumn;
import com.fengdui.oa.framework.constant.ConstantSession;
import com.fengdui.oa.framework.util.reflection.ReflectionUtil;
import com.fengdui.oa.framework.util.string.StringUtil;
import com.fengdui.oa.framework.web.PageInfo;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wander.Zeng
 * @data 2015-7-2 下午2:00:41
 * @desc MybatisService.java
 */
@Transactional
public abstract class MybatisService<T extends Serializable, PK, D extends MybatisDao<T, PK>> {

	private Class<T> entityClass;

	public MybatisService() {
		this.entityClass = ReflectionUtil.getSuperClassParam(getClass());
	}

	protected abstract D getDao();

	/**
	 * ---------------------------------------<insert>--------------------------
	 **/
	public int insert(T object) {
		if (checkUniqueCustom()) {
			if (!getDao().isUnique(object)) {
				throw new RuntimeException("非唯一记录！");
			}
		} else {
			List<List<Field>> fieldUnionList = getFieldUnionList();
			if (null != fieldUnionList) {
				for (List<Field> fieldList : fieldUnionList) {
					checkUnique4Insert(object, fieldList);
				}
			} else {
				checkUnique4Insert(object, getUniqueFieldList());
				/*List<Field> fieldList = getUniqueFieldList();
				if (!CollectionUtils.isEmpty(fieldList)) {
					try {
						List<String> colList = new ArrayList<String>();
						List<Object> valList = new ArrayList<Object>();
						List<String> colDescList = new ArrayList<String>();
						for (Field field : fieldList) {
							TagUniqueColumn tag = field.getAnnotation(TagUniqueColumn.class);
							colList.add(tag.colName());
							valList.add(field.get(object));
							colDescList.add(tag.colDesc());
						}
						if (isLogicDelete()) {
							colList.add(ConstantColumn.DELETED);
							valList.add(false);
						}
						if (!isUniqueByMultiValue(colList.toArray(new String[0]), valList.toArray())) {
							throw new RuntimeException("字段【" + StringUtil.convertListToString(colDescList, ConstantColumn.SEPARATOR) + "】已存在");
						}
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				} else {

				}*/
			}
		}
		return getDao().insert(object);
	}

	public int insertBySql(String sql) {
		return getDao().insertBySql(sql);
	}

	public int insertBatch(List<T> objList) {
		if (!CollectionUtils.isEmpty(objList)) {
			if (checkUniqueCustom() || checkUniqueColumn()) {
				for (T obj : objList) {
					insert(obj);
				}
				return objList.size();
			} else {
				return getDao().insertBatch(objList);
			}
		} else {
			return 0;
		}
	}

	/**
	 * ---------------------------------------<delete>--------------------------
	 **/
	public void delete(PK id) {
		delete(id, isLogicDelete());
	}

	public void delete(PK id, boolean isLogicDelete) {
		if (!isLogicDelete) {
			getDao().delete(id);
		} else {
			/*String[] cols = { ConstantColumn.DELETED, ConstantColumn.MODIFY_TIME, ConstantColumn.MODIFY_USER };
			Object[] vals = { true, "now()", StringUtil.escapeObj4SQL(ConstantSession.getInstance().getLoginname()) };
			getDao().updateMultiValueByValue(cols, vals, ConstantColumn.ID, id);*/
			updateValueByValue(ConstantColumn.DELETED, true, ConstantColumn.ID, id);
		}
	}

	@SuppressWarnings("unchecked")
	public void delete(T object) {
		if (object instanceof EntityBase) {
			EntityBase obj = (EntityBase) object;
			delete((PK) obj.getId());
		} else {
			getDao().delete(object);
		}
	}

	public void delete(List<PK> ids) {
		boolean isLogicDelete = isLogicDelete();
		if (!isLogicDelete) {
			getDao().deleteBatch(ids);
		} else {
			for (PK id : ids) {
				delete(id, isLogicDelete);
			}
		}
	}

	public void deleteBySql(String sql) {
		getDao().deleteBySql(sql);
	}

	public void deleteByValue(String col, Object val) {
		deleteByMultiValue(new String[] { col }, new Object[] { val });
	}

	public void deleteByMultiValue(String[] cols, Object[] vals) {
		if (!isLogicDelete()) {
			for (int i = 0; i < vals.length; i++) {
				vals[i] = StringUtil.escapeObj4SQL(vals[i]);
			}
			getDao().deleteByMultiValue(cols, vals);
		} else {
			updateValueByMultiValue(ConstantColumn.DELETED, true, cols, vals);
		}
	}

	/**
	 * ---------------------------------------<update>--------------------------
	 **/
	public void update(T object) {
		update(object, ConstantColumn.ID);
	}

	public void update(T object, String pkName) {
		if (object instanceof EntityBase) {
			EntityBase obj = (EntityBase) object;
			obj.setModifyTime(null);
			obj.setModifyUser(null);
		}
		if (checkUniqueCustom()) {
			if (!getDao().isUniqueExclude(object)) {
				throw new RuntimeException("非唯一记录！");
			}
		} else {
			List<List<Field>> fieldUnionList = getFieldUnionList();
			if (null != fieldUnionList) {
				for (List<Field> fieldList : fieldUnionList) {
					checkUnique4Update(object, pkName, fieldList);
				}
			} else {
				checkUnique4Update(object, pkName, getUniqueFieldList());
			}
			/*List<Field> fieldList = getUniqueFieldList();
			if (!CollectionUtils.isEmpty(fieldList)) {
				try {
					List<String> colList = new ArrayList<String>();
					List<Object> valList = new ArrayList<Object>();
					List<String> colDescList = new ArrayList<String>();
					for (Field field : fieldList) {
						TagUniqueColumn tag = field.getAnnotation(TagUniqueColumn.class);
						colList.add(tag.colName());
						valList.add(field.get(object));
						colDescList.add(tag.colDesc());
					}
					if (isLogicDelete()) {
						colList.add(ConstantColumn.DELETED);
						valList.add(false);
					}
					List<T> list = queryByMultiValue(colList.toArray(new String[0]), valList.toArray());
					Object pk = ReflectionUtil.getFieldValue(object, pkName);
					for (T obj : list) {
						Object pkQuery = ReflectionUtil.getFieldValue(obj, pkName);
						if (!pk.equals(pkQuery)) {
							throw new RuntimeException("字段【" + StringUtil.convertListToString(colDescList, ConstantColumn.SEPARATOR) + "】已存在");
						}
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}*/
		}
		getDao().update(object);
	}

	public void updateBySql(String sql) {
		getDao().updateBySql(sql);
	}

	public void updateValueByValue(String col, Object val, String keyCol, Object keyVal) {
		this.updateMultiValueByMultiValue(new String[] { col }, new Object[] { val }, new String[] { keyCol }, new Object[] { keyVal });
	}

	public void updateValueByMultiValue(String col, Object val, String[] keyCols, Object[] keyVals) {
		this.updateMultiValueByMultiValue(new String[] { col }, new Object[] { val }, keyCols, keyVals);
	}

	public void updateMultiValueByValue(String[] cols, Object[] vals, String keyCol, Object keyVal) {
		this.updateMultiValueByMultiValue(cols, vals, new String[] { keyCol }, new Object[] { keyVal });
	}

	public void updateMultiValueByMultiValue(String[] cols, Object[] vals, String[] keyCols, Object[] keyVals) {
		for (int i = 0; i < vals.length; i++) {
			vals[i] = StringUtil.escapeObj4SQL(vals[i]);
		}
		for (int i = 0; i < keyVals.length; i++) {
			keyVals[i] = StringUtil.escapeObj4SQL(keyVals[i]);
		}
		if (isLogicDelete()) {
			if (!ArrayUtils.contains(cols, ConstantColumn.MODIFY_TIME)) {
				cols = ArrayUtils.add(cols, ConstantColumn.MODIFY_TIME);
				vals = ArrayUtils.add(vals, "now()");
			}
			if (!ArrayUtils.contains(cols, ConstantColumn.MODIFY_USER)) {
				cols = ArrayUtils.add(cols, ConstantColumn.MODIFY_USER);
				vals = ArrayUtils.add(vals, StringUtil.escapeObj4SQL(ConstantSession.getInstance().getLoginname()));
			}
		}
		if (needAddDeleted(keyCols)) {
			keyCols = ArrayUtils.add(keyCols, ConstantColumn.DELETED);
			keyVals = ArrayUtils.add(keyVals, false);
		}
		getDao().updateMultiValueByMultiValue(cols, vals, keyCols, keyVals);
	}

	/**
	 * ---------------------------------------<select>--------------------------
	 **/
	public T get(PK id) {
		return queryUniqueByValue(ConstantColumn.ID, id);
	}

	public List<T> queryAll() {
		if (isLogicDelete()) {
			return queryByValue(ConstantColumn.DELETED, false);
		} else {
			return getDao().queryAll();
		}
	}

	public List<T> queryBySql(String sql) {
		return queryBySql(new String[] { sql });
	}

	public List<T> queryBySql(String sql, String order) {
		return queryBySql(new String[] { sql }, new String[] { order });
	}

	public List<T> queryBySql(String[] sqls) {
		if (needAddDeleted(sqls, false)) {
			sqls = ArrayUtils.add(sqls, ConstantColumn.DELETED + " = false");
		}
		return getDao().queryBySql(sqls, null);
	}

	public List<T> queryBySql(String[] sqls, String[] orders) {
		if (needAddDeleted(sqls, false)) {
			sqls = ArrayUtils.add(sqls, ConstantColumn.DELETED + " = false");
		}
		return getDao().queryBySql(sqls, orders);
	}

	public T queryUniqueByValue(String col, Object val) {
		return queryUniqueByMultiValue(new String[] { col }, new Object[] { val });
	}

	public T queryUniqueByMultiValue(String[] cols, Object[] vals) {
		for (int i = 0; i < vals.length; i++) {
			vals[i] = StringUtil.escapeObj4SQL(vals[i]);
		}
		if (needAddDeleted(cols)) {
			cols = ArrayUtils.add(cols, ConstantColumn.DELETED);
			vals = ArrayUtils.add(vals, false);
		}
		return getDao().queryUniqueByMultiValue(cols, vals);
	}

	public List<T> queryByValue(String col, Object val) {
		return queryByMultiValue(new String[] { col }, new Object[] { val });
	}

	public List<T> queryByMultiValue(String[] cols, Object[] vals) {
		for (int i = 0; i < vals.length; i++) {
			vals[i] = StringUtil.escapeObj4SQL(vals[i]);
		}
		if (needAddDeleted(cols)) {
			cols = ArrayUtils.add(cols, ConstantColumn.DELETED);
			vals = ArrayUtils.add(vals, false);
		}
		return getDao().queryByMultiValue(cols, vals);
	}

	public List<T> queryByValueByOrder(String col, Object val, String orderBy, String order) {
		return queryByMultiValueByOrder(new String[] { col }, new Object[] { val }, orderBy, order);
	}

	public List<T> queryByMultiValueByOrder(String[] cols, Object[] vals, String orderBy, String order) {
		return queryByMultiValueByMultiOrder(cols, vals, new String[] { orderBy }, new String[] { order });
	}

	public List<T> queryByMultiValueByMultiOrder(String[] cols, Object[] vals, String[] orderBys, String[] orders) {
		for (int i = 0; i < vals.length; i++) {
			vals[i] = StringUtil.escapeObj4SQL(vals[i]);
		}
		if (needAddDeleted(cols)) {
			cols = ArrayUtils.add(cols, ConstantColumn.DELETED);
			vals = ArrayUtils.add(vals, false);
		}
		return getDao().queryByMultiValueByMultiOrder(cols, vals, orderBys, orders);
	}

	public int queryCountByValue(String col, Object val) {
		return queryCountByMultiValue(new String[] { col }, new Object[] { val });
	}

	public int queryCountByMultiValue(String[] cols, Object[] vals) {
		for (int i = 0; i < vals.length; i++) {
			vals[i] = StringUtil.escapeObj4SQL(vals[i]);
		}
		if (needAddDeleted(cols)) {
			cols = ArrayUtils.add(cols, ConstantColumn.DELETED);
			vals = ArrayUtils.add(vals, false);
		}
		return getDao().queryCountByMultiValue(cols, vals);
	}

	public List<T> queryByPage(PageInfo pageInfo) {
		return queryByPage(pageInfo, new ArrayList<PageFilter>());
	}

	public List<T> queryByPage(PageInfo pageInfo, List<PageFilter> filters) {
		if (needAddDeleted(filters)) {
			filters.add(new PageFilter(ConstantColumn.DELETED + " = false"));
		}
		return getDao().queryByPage(pageInfo, filters);
	}

	public List<T> queryByLikeValue(String col, String val) {
		return getDao().queryByLikeValue(col, val);
	}

	/**
	 * ---------------------------------------<other>--------------------------
	 **/
	public void saveOrUpdate(T object) {
		saveOrUpdate(object, ConstantColumn.ID);
	}

	public void saveOrUpdate(T object, String pKey) {
		if (null == ReflectionUtil.invokeMethodOfGet(object, pKey)) {
			insert(object);
		} else {
			update(object, pKey);
		}
	}

	public boolean isUniqueByValue(String col, Object val) {
		return isUniqueByMultiValue(new String[] { col }, new Object[] { val });
	}

	public boolean isUniqueByMultiValue(String[] cols, Object[] vals) {
		for (int i = 0; i < vals.length; i++) {
			vals[i] = StringUtil.escapeObj4SQL(vals[i]);
		}
		if (needAddDeleted(cols)) {
			cols = ArrayUtils.add(cols, ConstantColumn.DELETED);
			vals = ArrayUtils.add(vals, false);
		}
		return getDao().isUniqueByMultiValue(cols, vals);
	}

	private boolean checkUniqueCustom() {
		return entityClass.isAnnotationPresent(TagUniqueCustom.class);
	}

	private boolean checkUniqueColumn() {
		return ReflectionUtil.hasAnnotationField(entityClass, TagUniqueColumn.class);
	}

	private List<List<Field>> getFieldUnionList() {
		List<List<Field>> fieldUnionlist = null;
		if (entityClass.isAnnotationPresent(TagUnionColumn.class)) {
			fieldUnionlist = new ArrayList<List<Field>>();
			TagUnionColumn tag = entityClass.getAnnotation(TagUnionColumn.class);
			String[] fieldNameUnionArray = tag.fieldNameUnion();
			for (String fieldNameUnion : fieldNameUnionArray) {
				String[] fieldNameArray = fieldNameUnion.split("#");
				List<Field> fieldList = new ArrayList<Field>();
				for (String fieldName : fieldNameArray) {
					try {
						Field field = entityClass.getDeclaredField(fieldName);
						field.setAccessible(true);
						fieldList.add(field);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
				fieldUnionlist.add(fieldList);
			}
		}
		return fieldUnionlist;
	}

	private List<Field> getUniqueFieldList() {
		return ReflectionUtil.getAnnotationFieldList(entityClass, TagUniqueColumn.class);
	}

	public boolean isLogicDelete() {
		return entityClass.isAnnotationPresent(TagLogicDelete.class);
	}

	private boolean needAddDeleted(String... cols) {
		return needAddDeleted(cols, true);
	}

	private boolean needAddDeleted(String[] cols, boolean fullMatch) {
		if (isLogicDelete()) {
			if (fullMatch) {
				return !ArrayUtils.contains(cols, ConstantColumn.DELETED);
			} else {
				for (String col : cols) {
					if (col.contains(ConstantColumn.DELETED)) {
						return false;
					}
				}
				return true;
			}
		} else {
			return false;
		}
	}

	private boolean needAddDeleted(List<PageFilter> filters) {
		if (isLogicDelete()) {
			for (PageFilter filter : filters) {
				if (StringUtils.isBlank(filter.getSql())) {
					if (filter.getPropertyName().equals(ConstantColumn.DELETED)) {
						return false;
					}
				} else {
					if (filter.getSql().contains(ConstantColumn.DELETED)) {
						return false;
					}
				}
			}
			return true;
		} else {
			return false;
		}
	}

	private void checkUnique4Insert(T object, List<Field> fieldList) {
		if (!CollectionUtils.isEmpty(fieldList)) {
			try {
				List<String> colList = new ArrayList<String>();
				List<Object> valList = new ArrayList<Object>();
				List<String> colDescList = new ArrayList<String>();
				for (Field field : fieldList) {
					TagUniqueColumn tag = field.getAnnotation(TagUniqueColumn.class);
					colList.add(tag.colName());
					valList.add(field.get(object));
					colDescList.add(tag.colDesc());
				}
				if (isLogicDelete()) {
					colList.add(ConstantColumn.DELETED);
					valList.add(false);
				}
				if (!isUniqueByMultiValue(colList.toArray(new String[0]), valList.toArray())) {
					throw new RuntimeException("字段【" + StringUtil.convertListToString(colDescList, ConstantColumn.SEPARATOR) + "】已存在");
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	private void checkUnique4Update(T object, String pkName, List<Field> fieldList) {
		if (!CollectionUtils.isEmpty(fieldList)) {
			try {
				List<String> colList = new ArrayList<String>();
				List<Object> valList = new ArrayList<Object>();
				List<String> colDescList = new ArrayList<String>();
				for (Field field : fieldList) {
					TagUniqueColumn tag = field.getAnnotation(TagUniqueColumn.class);
					colList.add(tag.colName());
					valList.add(field.get(object));
					colDescList.add(tag.colDesc());
				}
				if (isLogicDelete()) {
					colList.add(ConstantColumn.DELETED);
					valList.add(false);
				}
				List<T> list = queryByMultiValue(colList.toArray(new String[0]), valList.toArray());
				Object pk = ReflectionUtil.getFieldValue(object, pkName);
				for (T obj : list) {
					Object pkQuery = ReflectionUtil.getFieldValue(obj, pkName);
					if (!pk.equals(pkQuery)) {
						throw new RuntimeException("字段【" + StringUtil.convertListToString(colDescList, ConstantColumn.SEPARATOR) + "】已存在");
					}
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

}
