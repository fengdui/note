package com.fengdui.test.xhoa.framework.orm;

import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * @author Wander.Zeng
 * @data 2015-7-2 上午11:16:28
 * @desc MybatisDao.java
 */
public interface MybatisDao<T extends Serializable, PK> {

	/**
	 * ---------------------------------------<insert>--------------------------
	 **/
	int insert(T object);

	int insertBySql(@Param("sql") String sql);

	int insertBatch(List<T> objList);

	/**
	 * ---------------------------------------<delete>--------------------------
	 **/
	void delete(@Param("id") PK id);

	void delete(T object);

	int deleteBatch(List<PK> idList);

	void deleteBySql(@Param("sql") String sql);

	// void deleteByValue(@Param("col") String col, @Param("val") Object val);
	void deleteByMultiValue(@Param("cols") String[] cols, @Param("vals") Object[] vals);

	/**
	 * ---------------------------------------<update>--------------------------
	 **/
	void update(T object);

	void updateBySql(@Param("sql") String sql);

	// void updateValueByValue(@Param("col") String col, @Param("val") Object val, @Param("keyCol") String keyCol, @Param("keyVal") Object keyVal);
	// void updateValueByMultiValue(@Param("col") String col, @Param("val") Object val, @Param("keyCols") String[] keyCols, @Param("keyVals") Object[] keyVals);
	// void updateMultiValueByValue(@Param("cols") String[] cols, @Param("vals") Object[] vals, @Param("keyCol") String keyCol, @Param("keyVal") Object keyVal);

	void updateMultiValueByMultiValue(@Param("cols") String[] cols, @Param("vals") Object[] vals, @Param("keyCols") String[] keyCols, @Param("keyVals") Object[] keyVals);

	/**
	 * ---------------------------------------<select>--------------------------
	 **/
	// T query(@Param("id") PK id);

	List<T> queryAll();

	// List<T> queryBySql(@Param("sql") String sql);
	List<T> queryBySql(@Param("sqls") String[] sqls, @Param("orders") String[] orders);

	// List<T> queryAll(PageInfo<T> pageInfo);
	// List<T> queryAll(Object condition, PageInfo<T> pageInfo);
	// List<T> queryAllByPage(@Param("sql") String condition, @Param("page") Object pageInfo);

	// T queryUniqueByValue(@Param("col") String col, @Param("val") Object val);
	T queryUniqueByMultiValue(@Param("cols") String[] cols, @Param("vals") Object[] vals);

	// List<T> queryByValue(@Param("col") String col, @Param("val") Object val);
	List<T> queryByMultiValue(@Param("cols") String[] cols, @Param("vals") Object[] vals);

	// List<T> queryByValueByOrder(@Param("col") String col, @Param("val") Object val, @Param("orderBy") String orderBy, @Param("order") String order);
	// List<T> queryByMultiValueByOrder(@Param("cols") String[] cols, @Param("vals") Object[] vals, @Param("orderBy") String orderBy, @Param("order") String order);
	List<T> queryByMultiValueByMultiOrder(@Param("cols") String[] cols, @Param("vals") Object[] vals, @Param("orderBys") String[] orderBys, @Param("orders") String[] orders);

	// int queryCount(@Param("sql") String sql);
	// int queryCountByFilters(@Param("filters") List<PageFilter> filters);
	// int queryCountByValue(@Param("col") String col, @Param("val") Object val);
	int queryCountByMultiValue(@Param("cols") String[] cols, @Param("vals") Object[] vals);

	List<T> queryByPage(@Param("page") Object pageInfo, @Param("filters") List<PageFilter> filters);

	List<T> queryByLikeValue(@Param("col") String col, @Param("val") String val);

	// List<T> queryByMap(Map<?, ?> map);

	/**
	 * ---------------------------------------<other>--------------------------
	 **/
	// boolean isNotUniqueByOr(T object, String tableName, String names);
	// boolean isNotUniqueByAnd(T object, String tableName, String names);
	boolean isUnique(T object);

	boolean isUniqueExclude(T object);

	// boolean isUniqueByValue(@Param("obj") T object, @Param("cols") String... cols);
	boolean isUniqueByMultiValue(@Param("cols") String[] cols, @Param("vals") Object[] vals);

}
