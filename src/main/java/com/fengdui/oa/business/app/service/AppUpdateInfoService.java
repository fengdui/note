package com.fengdui.oa.business.app.service;

import com.fengdui.oa.business.app.dao.AppUpdateInfoDao;
import com.fengdui.oa.business.app.entity.AppUpdateInfo;
import com.fengdui.oa.framework.orm.MybatisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fd
 * @date 2015年10月19日
 * @desc AppUpdateInfoService.java
 */
@Service
public class AppUpdateInfoService extends MybatisService<AppUpdateInfo, Integer, AppUpdateInfoDao> {

	@Autowired
	private AppUpdateInfoDao appUpdateInfoDao;
	@Override
	protected AppUpdateInfoDao getDao() {
		return appUpdateInfoDao;
	}
	public List<AppUpdateInfo> queryByVersion(String sql) {
		return getDao().queryByVersion(sql);
	}
	public boolean insertCheck(Integer versionNum, Integer versionNum2) {
		String sql = "a.version_st <= " + versionNum + " and a.version_ed >= " + versionNum;
		List<AppUpdateInfo> illegalList = queryByVersion(sql);
		if(illegalList != null && illegalList.size() > 0) {
			return false;
		}
		if(versionNum2 != null) {
			String sql2 = "b.version_num >= " + versionNum + " and b.version_num <= " + versionNum2;
			List<AppUpdateInfo> illegalList2 = queryByVersion(sql2);
			if(illegalList2 != null && illegalList2.size() > 0) {
				return false;
			}
		}
		return true;
	}
}
