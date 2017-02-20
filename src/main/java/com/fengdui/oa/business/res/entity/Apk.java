package com.fengdui.oa.business.res.entity;

import com.fengdui.oa.framework.common.IdNamePair;
import com.fengdui.oa.framework.orm.EntityExtend;
import com.fengdui.oa.framework.orm.TagLogicDelete;
import com.fengdui.oa.framework.orm.TagUniqueColumn;
import com.fengdui.oa.framework.util.string.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * @author Wander.Zeng
 * @data 2015-9-8 上午10:28:25
 * @desc Apk.java
 */
@TagLogicDelete
public class Apk extends EntityExtend {

	private static final long serialVersionUID = 112571795365095630L;

	public static final String[] DENSITY_ARRAY = { "120", "160", "240", "320", "480" };// { "120", "160", "240", "320", "480", "640" }
	public static final String[] DPI_ARRAY = { "ldpi", "mdpi", "hdpi", "xhdpi", "xxhdpi" };// { "ldpi", "mdpi", "hdpi", "xhdpi", "xxhdpi", "xxxhdpi" } xxxhdpi用于电视等 4K 屏
	public static final int[][] LIMIT_DIMENSIONS_SCREENSHOT = { { 320, 240 }, { 480, 320 }, { 960, 540 }, { 1280, 720 }, { 1920, 1080 } };
	// public static final int DPI_TYPE_DEFAULT = 2;
	public static final int DPI_TYPE_DEFAULT_APPOINT = 2;// mdpi
	public static final IdNamePair[] STATUS_ARRAY = { new IdNamePair(StatusEnum.OFF_LIB.getVal(), StatusEnum.OFF_LIB.getName()),
			new IdNamePair(StatusEnum.ON_LIB.getVal(), StatusEnum.ON_LIB.getName()), new IdNamePair(StatusEnum.ON_LINE.getVal(), StatusEnum.ON_LINE.getName()) };

	public enum StatusEnum {
		OFF_LIB(0, "未入库"), ON_LIB(1, "入库"), ON_LINE(2, "线上");

		private StatusEnum(int val, String name) {
			this.val = val;
			this.name = name;
		}

		private int val;
		private String name;

		public int getVal() {
			return val;
		}

		public String getName() {
			return name;
		}

	}


	private String name;
	@TagUniqueColumn(colName = "package_name", colDesc = "包名")
	private String packageName;
	@TagUniqueColumn(colName = "version_code", colDesc = "版本号")
	private Integer versionCode;
	private String versionName;
	private Integer appId;
	private Integer status;
	private String fileId;
	private Long fileSize;
	private String fileMd5;
	private String description;

	private String iconId;
	private List<ApkIcon> iconList;
	private int screenshotCount;
	private List<List<ApkScreenshot>> screenshotList;
	private App app;

	public Apk() {

	}

	public Apk(ApkInfo apkInfo) {
		this.name = apkInfo.getApplicationLabel().get(ApkInfo.KEY_DEFAULT);
		this.packageName = apkInfo.getPackageName();
		this.versionCode = apkInfo.getVersionCode();
		this.versionName = apkInfo.getVersionName();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public Integer getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(Integer versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public Integer getStatus() {
		return status;
	}

	public String getStatusShow() {
		return STATUS_ARRAY[status].getName();
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileMd5() {
		return fileMd5;
	}

	public void setFileMd5(String fileMd5) {
		this.fileMd5 = fileMd5;
	}

	public String getDescription() {
		if (StringUtils.isBlank(description)) {
			return null;
		}
		return StringUtil.subString(description, 256);
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIconId() {
		return iconId;
	}

	public String getIconURL() throws Exception {
		return "";
//		return FileSystemManage.getFileURL(iconId);
	}

	public void setIconId(String iconId) {
		this.iconId = iconId;
	}

	public List<ApkIcon> getIconList() {
		return iconList;
	}

	public void setIconList(List<ApkIcon> iconList) {
		this.iconList = iconList;
	}

	public int getScreenshotCount() {
		return screenshotCount;
	}

	public void setScreenshotCount(int screenshotCount) {
		this.screenshotCount = screenshotCount;
	}

	public List<List<ApkScreenshot>> getScreenshotList() {
		return screenshotList;
	}

	public void setScreenshotList(List<List<ApkScreenshot>> screenshotList) {
		this.screenshotList = screenshotList;
	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

}
