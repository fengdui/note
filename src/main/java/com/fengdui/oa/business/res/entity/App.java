package com.fengdui.oa.business.res.entity;

import com.xh.market.framework.common.IdNamePair;
import com.xh.market.framework.filesystem.FileSystemManage;
import com.xh.market.framework.orm.EntityExtend;
import com.xh.market.framework.orm.TagLogicDelete;
import com.xh.market.framework.orm.TagUniqueColumn;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * @author Wander.Zeng
 * @data 2015-9-24 上午10:29:40
 * @desc App.java
 */
@TagLogicDelete
public class App extends EntityExtend {

	private static final long serialVersionUID = 8174160887103658849L;

	public static final IdNamePair[] STATUS_ARRAY = { new IdNamePair(StatusEnum.OFF.getVal(), StatusEnum.OFF.getName()), new IdNamePair(StatusEnum.ON.getVal(), StatusEnum.ON.getName()) };

	public enum StatusEnum {
		OFF(0, "下架"), ON(1, "上架");

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
	private Integer apkId;
	private Integer status;
	private String description;

	private Apk apk;
	private String iconId;
	private List<Apk> apkList;
	private List<ApkIcon> iconList;
	private List<ApkScreenshot> screenshotList;

	public App() {

	}

	public App(Apk apk) {
		this.name = apk.getName();
		this.packageName = apk.getPackageName();
		this.apkId = apk.getId();
		this.status = StatusEnum.ON.getVal();

		this.apk = apk;
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

	public Integer getApkId() {
		return apkId;
	}

	public void setApkId(Integer apkId) {
		this.apkId = apkId;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Apk getApk() {
		return apk;
	}

	public void setApk(Apk apk) {
		this.apk = apk;
	}

	public String getIconId() {
		return iconId;
	}

	public String getIconURL() throws Exception {
		return FileSystemManage.getFileURL(iconId);
	}

	public void setIconId(String iconId) {
		this.iconId = iconId;
	}

	public List<Apk> getApkList() {
		return apkList;
	}

	public void setApkList(List<Apk> apkList) {
		this.apkList = apkList;
	}

	public List<ApkIcon> getIconList() {
		return iconList;
	}

	public void setIconList(List<ApkIcon> iconList) {
		this.iconList = iconList;
	}

	public List<ApkScreenshot> getScreenshotList() {
		return screenshotList;
	}

	public void setScreenshotList(List<ApkScreenshot> screenshotList) {
		this.screenshotList = screenshotList;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

}
