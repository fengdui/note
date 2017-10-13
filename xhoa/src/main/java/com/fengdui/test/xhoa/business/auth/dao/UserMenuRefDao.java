package com.fengdui.oa.business.auth.dao;

import com.fengdui.oa.business.auth.entity.UserMenuRef;
import com.fengdui.oa.framework.orm.MybatisDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wander.Zeng
 * @data 2015-8-18 下午5:16:35
 * @desc UserMenuRefDao.java
 */
@Repository
public interface UserMenuRefDao extends MybatisDao<UserMenuRef, Integer> {

	public String getMenuListStr(@Param("userId") Integer userId);

	public List<Integer> getMenuList(@Param("userId") Integer userId);

	public List<Integer> getUserList(@Param("menuId") Integer menuId);

}
