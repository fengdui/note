package com.fengdui.test.xhoa.business.res.dao;

import com.fengdui.test.xhoa.business.res.entity.App;
import com.fengdui.test.xhoa.framework.orm.MybatisDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Wander.Zeng
 * @data 2015-9-24 上午10:35:11
 * @desc AppDao.java
 */
@Repository
public interface AppDao extends MybatisDao<App, Integer> {

	public Integer getIdByPackageName(@Param("packageName") String packageName);

}
