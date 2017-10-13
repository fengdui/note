package com.fengdui.oa.business.sys.dao;

import com.fengdui.oa.business.sys.entity.RolegroupMenuRef;
import com.fengdui.oa.framework.orm.MybatisDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wander.Zeng
 * @data 2015-8-25 下午5:48:31
 * @desc RolegroupMenuRefDao.java
 */
@Repository
public interface RolegroupMenuRefDao extends MybatisDao<RolegroupMenuRef, Integer> {

	public String getMenuListStr(@Param("rolegroupId") Integer rolegroupId);

	public List<Integer> getMenuList(@Param("rolegroupId") Integer rolegroupId);

	public List<Integer> getRolegroupList(@Param("menuId") Integer menuId);

}
