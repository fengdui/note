package com.fengdui.test.xhoa.business.app.dao;

import com.fengdui.test.xhoa.business.app.entity.AppVersionInfo;
import com.fengdui.test.xhoa.framework.orm.MybatisDao;
import org.springframework.stereotype.Repository;

/**
 * @author fd
 * @date 2015年10月22日
 * @desc AppVersionInfoDao.java
 */
@Repository
public interface AppVersionInfoDao extends MybatisDao<AppVersionInfo, Integer> {

}
