package com.fengdui.oa.business.app.dao;

import com.fengdui.oa.business.app.entity.AppUpdateInfo;
import com.fengdui.oa.framework.orm.MybatisDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author fd
 * @date 2015年10月19日
 * @desc AppUpdateInfoDao.java
 */

@Repository
public interface AppUpdateInfoDao extends MybatisDao<AppUpdateInfo, Integer> {
	public List<AppUpdateInfo> queryByVersion(@Param("sql") String sql);
}
