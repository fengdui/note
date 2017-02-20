package com.fengdui.oa.business.sys.dao;

import com.fengdui.oa.business.sys.entity.RoleMenuRef;
import com.fengdui.oa.framework.orm.MybatisDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wander.Zeng
 * @data 2015-8-10 下午7:11:18
 * @desc RoleMenuRefDao.java
 */
@Repository
public interface RoleMenuRefDao extends MybatisDao<RoleMenuRef, Integer> {

	public String getMenuListStr(@Param("roleId") Integer roleId);

	public List<Integer> getMenuList(@Param("roleId") Integer roleId);

	public List<Integer> getRoleList(@Param("menuId") Integer menuId);

}
