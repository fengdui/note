package com.fengdui.oa.business.sys.dao;

import com.xh.market.business.sys.entity.Role;
import com.xh.market.framework.orm.MybatisDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wander.Zeng
 * @data 2015-8-3 下午2:56:10
 * @desc RoleDao.java
 */
@Repository
public interface RoleDao extends MybatisDao<Role, Integer> {

	public String getNameById(@Param("id") Integer id);
	
	public int getRolegroupIdById(@Param("id") Integer id);

	public List<Integer> getIdListByRolegroupId(@Param("rolegroupId") Integer rolegroupId);

}
