package com.fengdui.test.xhoa.business.res.service;

import com.fengdui.test.xhoa.business.common.Constant;
import com.fengdui.test.xhoa.business.res.dao.ApkDao;
import com.fengdui.test.xhoa.business.res.dao.AppDao;
import com.fengdui.test.xhoa.business.res.entity.Apk;
import com.fengdui.test.xhoa.business.res.entity.ApkIcon;
import com.fengdui.test.xhoa.business.res.entity.ApkInfo;
import com.fengdui.test.xhoa.business.res.entity.ApkScreenshot;
import com.fengdui.test.xhoa.framework.orm.MybatisService;
import com.fengdui.test.xhoa.framework.util.ImageUtil;
import com.fengdui.test.xhoa.framework.util.file.ApkUtil;
import com.fengdui.test.xhoa.framework.util.file.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Wander.Zeng
 * @data 2015-9-8 上午10:36:43
 * @desc ApkService.java
 */
@Service
public class ApkService extends MybatisService<Apk, Integer, ApkDao> {

	@Autowired
	private ApkDao apkDao;
	@Autowired
	private AppDao appDao;
	@Autowired
	private ApkIconService apkIconService;
	@Autowired
	private ApkScreenshotService apkScreenshotService;

	@Override
	protected ApkDao getDao() {
		return apkDao;
	}

	public Apk getApk(Integer id) {
		Apk apk = get(id);
		apk.setIconList(apkIconService.getIconListByApkId(id));
		apk.setScreenshotList(apkScreenshotService.getListByApkId(id));
		return apk;
	}

	public String importApk(MultipartFile fileSrc) throws Exception {
		File fileTemp = null;
		try {
			fileTemp = Constant.saveTempFile4Apk(fileSrc);
			return fileTemp.getAbsolutePath();
		} catch (Exception e) {
			if (null != fileTemp && fileTemp.exists()) {
				fileTemp.delete();
			}
			throw e;
		}
	}

	public void handleApk(String fileName, String filePath) throws Exception {
		File fileTemp = new File(filePath);
		Apk apk = null;
		List<ApkIcon> apkIconList = null;
		try {
			String fileMD5 = FileUtil.getMD5(fileTemp);
			if (!isUniqueByValue("file_md5", fileMD5)) {
				throw new Exception("相同文件已存在");
			}

			ApkInfo apkInfo = ApkUtil.getApkInfo(fileTemp);
			apkIconList = ApkUtil.getApkIcon(fileTemp, apkInfo.getApplicationIcon());

			apk = new Apk(apkInfo);
//			apk.setFileId(FileSystemManage.upload4APK(fileName, fileTemp));
			apk.setFileSize(fileTemp.length());
			apk.setFileMd5(fileMD5);
			saveOrUpdate(apk);

			for (ApkIcon apkIcon : apkIconList) {
				apkIcon.setApkId(apk.getId());
				apkIconService.saveOrUpdate(apkIcon);
			}
		} catch (Exception e) {
			if (null != apk) {
//				FileSystemManage.delete(apk.getFileId());
			}
			if (null != apkIconList) {
				for (ApkIcon apkIcon : apkIconList) {
					if (null != apkIcon.getFileId()) {
//						FileSystemManage.delete(apkIcon.getFileId());
					}
				}
			}
			throw new RuntimeException(e);
		} finally {
			if (null != fileTemp && fileTemp.exists()) {
				fileTemp.delete();
			}
			if (null != apkIconList) {
				for (ApkIcon apkIcon : apkIconList) {
					if (null != apkIcon.getFile() && apkIcon.getFile().exists()) {
						apkIcon.getFile().delete();
					}
				}
			}
		}
	}

	/*public void importApk(MultipartFile fileSrc) throws Exception {
		File fileTemp = null;
		Apk apk = null;
		List<ApkIcon> apkIconList = null;
		try {
			fileTemp = Constant.saveTempFile4Apk(fileSrc);
			ApkInfo apkInfo = ApkUtil.getApkInfo(fileTemp);
			apkIconList = ApkUtil.getApkIcon(fileTemp, apkInfo.getApplicationIcon());

			apk = new Apk(apkInfo);
			apk.setFileId(FileSystemManage.upload4APK(fileSrc, fileTemp));
			apk.setFileSize(fileSrc.getSize());
			apk.setFileMd5(FileUtil.getMD5(fileTemp));
			saveOrUpdate(apk);

			for (ApkIcon apkIcon : apkIconList) {
				apkIcon.setApkId(apk.getId());
				apkIconService.saveOrUpdate(apkIcon);
			}
		} catch (Exception e) {
			if (null != apk) {
				FileSystemManage.delete(apk.getFileId());
			}
			if (null != apkIconList) {
				for (ApkIcon apkIcon : apkIconList) {
					if (null != apkIcon.getFileId()) {
						FileSystemManage.delete(apkIcon.getFileId());
					}
				}
			}
			throw new RuntimeException(e);
		} finally {
			if (null != fileTemp && fileTemp.exists()) {
				fileTemp.delete();
			}
			if (null != apkIconList) {
				for (ApkIcon apkIcon : apkIconList) {
					if (null != apkIcon.getFile() && apkIcon.getFile().exists()) {
						apkIcon.getFile().delete();
					}
				}
			}
		}
	}*/

	public String importScreenshot(MultipartFile fileSrc) throws Exception {
		File fileTemp = null;
		try {
			fileTemp = Constant.saveTempFile4Screenshot(fileSrc);
			return fileTemp.getAbsolutePath();
		} catch (Exception e) {
			if (null != fileTemp && fileTemp.exists()) {
				fileTemp.delete();
			}
			throw e;
		}
	}

	public List<ApkScreenshot> handleScreenshot(String filePath) throws Exception {
		File fileTemp = new File(filePath);
		List<ApkScreenshot> screenshotList = new ArrayList<ApkScreenshot>();
		List<File> screenFileList = new ArrayList<File>();
		try {
			String fileTempParentPath = fileTemp.getParent() + File.separator;
			String fileTempName = fileTemp.getName();
			String fileTempNamePre = fileTempName.substring(0, fileTempName.lastIndexOf("."));
			String fileTempNameSuf = fileTempName.substring(fileTempName.lastIndexOf("."));
			Map<String, Integer> dimensionsOriginal = ImageUtil.getImageDimensions(fileTemp);
			int widthOriginal = dimensionsOriginal.get(ImageUtil.KEY_WIDTH);
			int heightOriginal = dimensionsOriginal.get(ImageUtil.KEY_HEIGHT);
			for (int i = 0; i < Apk.LIMIT_DIMENSIONS_SCREENSHOT.length; i++) {
				int[] dimensions = Apk.LIMIT_DIMENSIONS_SCREENSHOT[i];
				int widthLimit = 0;
				int heightLimit = 0;
				if (heightOriginal >= widthOriginal) {
					heightLimit = Math.max(dimensions[0], dimensions[1]);
					widthLimit = Math.min(dimensions[0], dimensions[1]);
				} else {
					heightLimit = Math.min(dimensions[0], dimensions[1]);
					widthLimit = Math.max(dimensions[0], dimensions[1]);
				}
				float widthZoom = (float) widthLimit / (float) widthOriginal;
				float heightZoom = (float) heightLimit / (float) heightOriginal;
				float zoom = Math.min(widthZoom, heightZoom);

				String fileTar = fileTempParentPath + fileTempNamePre + "_" + (i + 1) + fileTempNameSuf;
				ImageUtil.resizeImageByLib(fileTemp, fileTar, zoom);
				File screenFile = new File(fileTar);
				Map<String, Integer> dimensionsNew = ImageUtil.getImageDimensions(screenFile);
//				screenshotList.add(new ApkScreenshot(FileSystemManage.upload4Image(screenFile), (i + 1), dimensionsNew.get(ImageUtil.KEY_WIDTH), dimensionsNew.get(ImageUtil.KEY_HEIGHT)));
				screenFileList.add(screenFile);
			}
			return screenshotList;
		} catch (Exception e) {
			for (ApkScreenshot screenshot : screenshotList) {
//				FileSystemManage.delete(screenshot.getFileId());
			}
			throw new Exception(e);
		} finally {
			if (null != fileTemp && fileTemp.exists()) {
				fileTemp.delete();
			}
			for (File screen : screenFileList) {
				if (null != screen && screen.exists()) {
					screen.delete();
				}
			}
		}
	}

	public List<Apk> getListOnLib(String packageName) {
		return getDao().getListOnLib(packageName);
	}

	public void save(Apk apk) {
		update(apk);
		apkScreenshotService.saveScreenshotList(apk);
	}

	public void updateStatus(int apkId, int appId, int status) {
		updateMultiValueByValue(new String[] { "app_id", "status" }, new Object[] { appId, status }, "id", apkId);
	}

	public void offLine(int appId) {
		updateValueByMultiValue("status", Apk.StatusEnum.ON_LIB.getVal(), new String[] { "app_id", "status" }, new Object[] { appId, Apk.StatusEnum.ON_LINE.getVal() });
	}

	public void onLib(Integer id, String packageName) throws Exception {
		Integer appId = appDao.getIdByPackageName(packageName);
		if (null == appId) {
			throw new Exception("包名为[" + packageName + "]的应用没找到");
		}
		updateStatus(id, appId, Apk.StatusEnum.ON_LIB.getVal());
	}

	public void offLibByAppId(Integer appId) {
		updateMultiValueByValue(new String[] { "app_id", "status" }, new Object[] { null, Apk.StatusEnum.OFF_LIB.getVal() }, "app_id", appId);
	}

	public void offLibByApkId(Integer apkId) {
		updateMultiValueByValue(new String[] { "app_id", "status" }, new Object[] { null, Apk.StatusEnum.OFF_LIB.getVal() }, "id", apkId);
	}

	public void deleteApk(List<Integer> idList) throws Exception {
		for (int id : idList) {
			String apkFileId = getFileIdById(id);
			delete(id);
			apkIconService.deleteApkIcon(id);
			apkScreenshotService.deleteScreenshot(id);
//			FileSystemManage.delete(apkFileId);
		}
	}

	public String getFileIdById(Integer id) {
		return apkDao.getFileIdById(id);
	}

}
