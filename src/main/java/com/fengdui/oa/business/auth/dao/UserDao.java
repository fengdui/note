package com.fengdui.oa.business.auth.dao;

import com.fengdui.oa.business.auth.entity.User;
import com.fengdui.oa.framework.orm.MybatisDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wander.Zeng
 * @data 2015-7-2 上午11:07:54
 * @desc UserDao.java
 */
@Repository
public interface UserDao extends MybatisDao<User, Integer> {

	public List<Integer> getIdListByRoleId(@Param("roleId") Integer roleId);

}
