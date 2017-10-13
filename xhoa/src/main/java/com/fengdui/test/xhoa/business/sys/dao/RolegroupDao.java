package com.fengdui.test.xhoa.business.sys.dao;

import com.fengdui.test.xhoa.business.sys.entity.Rolegroup;
import com.fengdui.test.xhoa.framework.orm.MybatisDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Wander.Zeng
 * @data 2015-8-25 下午4:19:33
 * @desc RolegroupDao.java
 */
@Repository
public interface RolegroupDao extends MybatisDao<Rolegroup, Integer> {

	public String getNameById(@Param("id") Integer id);

}
