package com.fengdui.test.xhoa.business.res.service;

import com.fengdui.test.xhoa.business.res.dao.ApkIconDao;
import com.fengdui.test.xhoa.business.res.entity.Apk;
import com.fengdui.test.xhoa.business.res.entity.ApkIcon;
import com.fengdui.test.xhoa.framework.constant.ConstantColumn;
import com.fengdui.test.xhoa.framework.orm.MybatisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Wander.Zeng
 * @data 2015-9-14 下午9:27:38
 * @desc ApkIconService.java
 */
@Service
public class ApkIconService extends MybatisService<ApkIcon, Integer, ApkIconDao> {

	@Autowired
	private ApkIconDao apkIconDao;

	@Override
	protected ApkIconDao getDao() {
		return apkIconDao;
	}

	public String getDefaultIconFileId(int apkId) {
		return apkIconDao.getIconFileId(apkId, Apk.DPI_TYPE_DEFAULT_APPOINT);
	}

	public List<String> getIconFileIdListByApkId(int apkId) {
		return apkIconDao.getIconFileIdListByApkId(apkId);
	}

	public List<ApkIcon> getIconListByApkId(int apkId) {
		return queryByValueByOrder("apk_id", apkId, "dpi_type", ConstantColumn.ORDER_ASC);
	}

	public void deleteApkIcon(Integer apkId) throws Exception {
		List<String> iconFileIdList = getIconFileIdListByApkId(apkId);
		deleteByValue("apk_id", apkId);
		for (String iconFileId : iconFileIdList) {
//			FileSystemManage.delete(iconFileId);
		}
	}
}
