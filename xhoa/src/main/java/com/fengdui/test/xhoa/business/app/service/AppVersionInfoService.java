package com.fengdui.test.xhoa.business.app.service;

import com.fengdui.test.xhoa.business.app.dao.AppVersionInfoDao;
import com.fengdui.test.xhoa.business.app.entity.AppVersionInfo;
import com.fengdui.test.xhoa.framework.orm.MybatisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fd
 * @date 2015年10月22日
 * @desc AppVersionInfoService.java
 */
@Service
public class AppVersionInfoService extends MybatisService<AppVersionInfo, Integer, AppVersionInfoDao> {

	@Autowired
	private AppVersionInfoDao appVersionInfoDao;
	@Override
	protected AppVersionInfoDao getDao() {
		return appVersionInfoDao;
	}
	public boolean versionCheck(Integer versionNum) {
		String sql = versionNum+" <= (select max(version_num) from tbl_app_version)";
		List<AppVersionInfo> illegalList = getDao().queryBySql(new String[]{sql}, new String[]{});
		if(illegalList != null && illegalList.size() > 0) {
			return false;
		}
		return true;
	}
}
