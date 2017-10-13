package com.fengdui.oa.business.res.dao;

import com.fengdui.oa.business.res.entity.AppClass;
import com.fengdui.oa.framework.orm.MybatisDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Soda.Yang
 * @date 2015-9-22 上午11:02:25
 * @desc AppClassDao.java
 */
@Repository
public interface AppClassDao extends MybatisDao<AppClass, Integer> {

	/**
	 * 查询应用分类列表
	 * 
	 * @return
	 */
	public List<AppClass> getAppClassList();

	/**
	 * @Title: getAppClassListByParentId
	 * @Description:
	 * @param: @return
	 * @return: List<AppClass>
	 * @throws
	 */
	public List<AppClass> getAppClassListByParentId(@Param("parentId") Integer parentId);

}
