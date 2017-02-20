package com.fengdui.oa.business.app.dao;

import com.fengdui.oa.business.app.entity.AppVersionInfo;
import com.fengdui.oa.framework.orm.MybatisDao;
import org.springframework.stereotype.Repository;

/**
 * @author fd
 * @date 2015年10月22日
 * @desc AppVersionInfoDao.java
 */
@Repository
public interface AppVersionInfoDao extends MybatisDao<AppVersionInfo, Integer> {

}
