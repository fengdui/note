package com.fengdui.oa.business.res.service;

import com.xh.market.business.res.dao.ApkScreenshotDao;
import com.xh.market.business.res.entity.Apk;
import com.xh.market.business.res.entity.ApkScreenshot;
import com.xh.market.framework.constant.ConstantColumn;
import com.xh.market.framework.filesystem.FileSystemManage;
import com.xh.market.framework.orm.MybatisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wander.Zeng
 * @data 2015-9-22 上午11:47:07
 * @desc ApkScreenshotService.java
 */
@Service
public class ApkScreenshotService extends MybatisService<ApkScreenshot, Integer, ApkScreenshotDao> {

	@Autowired
	private ApkScreenshotDao apkScreenshotDao;

	@Override
	protected ApkScreenshotDao getDao() {
		return apkScreenshotDao;
	}

	public int getCountByApkId(int apkId) {
		return queryCountByValue("apk_id", apkId);
	}

	public List<ApkScreenshot> getDefaultListByApkId(int apkId) {
		return queryByMultiValueByOrder(new String[] { "apk_id", "dpi_type" }, new Object[] { apkId, Apk.DPI_TYPE_DEFAULT_APPOINT }, "seq", ConstantColumn.ORDER_ASC);
	}

	public List<List<ApkScreenshot>> getListByApkId(int apkId) {
		List<ApkScreenshot> screenshotList = queryByMultiValueByMultiOrder(new String[] { "apk_id" }, new Object[] { apkId }, new String[] { "seq", "dpi_type" }, new String[] {
				ConstantColumn.ORDER_ASC, ConstantColumn.ORDER_ASC });
		List<List<ApkScreenshot>> list = new ArrayList<List<ApkScreenshot>>();
		int seqLast = -1;
		for (ApkScreenshot screenshot : screenshotList) {
			if (seqLast != screenshot.getSeq()) {
				List<ApkScreenshot> listDpi = new ArrayList<ApkScreenshot>();
				listDpi.add(screenshot);
				list.add(listDpi);
			} else {
				list.get((list.size() - 1)).add(screenshot);
			}
			seqLast = screenshot.getSeq();
		}
		return list;
	}

	public void saveScreenshotList(Apk apk) {
		List<List<ApkScreenshot>> screenshotList = apk.getScreenshotList();
		apkScreenshotDao.deleteByApkIdAndSeq(apk.getId(), screenshotList.size());
		for (int i = 0; i < screenshotList.size(); i++) {
			List<ApkScreenshot> screenshotList4Dpi = screenshotList.get(i);
			for (int j = 0; j < screenshotList4Dpi.size(); j++) {
				ApkScreenshot screenshot = screenshotList4Dpi.get(j);
				ApkScreenshot screenshotOld = queryUniqueByMultiValue(new String[] { "apk_id", "dpi_type", "seq" },
						new Object[] { apk.getId(), screenshot.getDpiType(), screenshot.getSeq() });
				if (null != screenshotOld) {
					if (!screenshotOld.getFileId().equals(screenshot.getFileId())) {
						updateValueByMultiValue("file_id", screenshot.getFileId(), new String[] { "apk_id", "dpi_type", "seq" }, new Object[] { apk.getId(), screenshot.getDpiType(),
								screenshot.getSeq() });
					}
				} else {
					insert(screenshot);
				}
			}
		}
	}

	public void deleteScreenshot(Integer apkId) throws Exception {
		List<String> fileIdList = getDao().getFileIdListByApkId(apkId);
		deleteByValue("apk_id", apkId);
		for (String fileId : fileIdList) {
			FileSystemManage.delete(fileId);
		}
	}

}
