package com.fengdui.test.xhoa.business.res.service;

import com.fengdui.test.xhoa.business.res.dao.AppDao;
import com.fengdui.test.xhoa.business.res.entity.Apk;
import com.fengdui.test.xhoa.business.res.entity.App;
import com.fengdui.test.xhoa.framework.orm.MybatisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Wander.Zeng
 * @data 2015-9-24 上午10:43:40
 * @desc AppService.java
 */
@Service
public class AppService extends MybatisService<App, Integer, AppDao> {

	@Autowired
	private AppDao appDao;
	@Autowired
	private ApkService apkService;
	@Autowired
	private ApkIconService apkIconService;
	@Autowired
	private ApkScreenshotService apkScreenshotService;

	@Override
	protected AppDao getDao() {
		return appDao;
	}

	public App getByPackageName(String packageName) {
		return queryUniqueByValue("package_name", packageName);
	}

	public App getApp(Integer id) {
		App app = get(id);
		app = addImageList(app);
		app.setApk(apkService.get(app.getApkId()));
		app.setApkList(apkService.getListOnLib(app.getPackageName()));
		return app;
	}

	public App addImageList(App app) {
		app.setIconList(apkIconService.getIconListByApkId(app.getApkId()));
		app.setScreenshotList(apkScreenshotService.getDefaultListByApkId(app.getApkId()));
		return app;
	}

	public void save(App app) {
		saveOrUpdate(app);
		apkService.offLine(app.getId());
		apkService.updateStatus(app.getApkId(), app.getId(), Apk.StatusEnum.ON_LINE.getVal());
	}

	public void changeStatus(Integer id, Integer status) {
		if (status == App.StatusEnum.ON.getVal()) {
			status = App.StatusEnum.OFF.getVal();
		} else {
			status = App.StatusEnum.ON.getVal();
		}
		updateValueByValue("status", status, "id", id);
	}

	public void deleteApp(List<Integer> idList) throws Exception {
		for (int id : idList) {
			delete(id);
			apkService.offLibByAppId(id);
		}
	}

}
