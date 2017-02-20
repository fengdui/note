package com.fengdui.oa.business.res.entity;

import com.fengdui.oa.framework.orm.EntityExtend;
import com.fengdui.oa.framework.orm.TagUniqueColumn;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.File;

/**
 * @author Wander.Zeng
 * @data 2015-9-14 下午2:37:32
 * @desc ApkIcon.java
 */
public class ApkIcon extends EntityExtend {

	private static final long serialVersionUID = 3572251157615072468L;

	@TagUniqueColumn(colName = "apk_id", colDesc = "apk id")
	private Integer apkId;
	@TagUniqueColumn(colName = "dpi_type", colDesc = "dpi类型")
	private Integer dpiType;
	private String fileId;
	private Integer width;
	private Integer height;

	private File file;

	public ApkIcon() {

	}

	public ApkIcon(Integer dpiType, String fileId, File file, Integer width, Integer height) {
		this.dpiType = dpiType;
		this.fileId = fileId;
		this.file = file;
		this.width = width;
		this.height = height;
	}

	public Integer getApkId() {
		return apkId;
	}

	public void setApkId(Integer apkId) {
		this.apkId = apkId;
	}

	public Integer getDpiType() {
		return dpiType;
	}

	public String getDpi() {
		return Apk.DPI_ARRAY[dpiType - 1].toUpperCase();
	}

	public void setDpiType(Integer dpiType) {
		this.dpiType = dpiType;
	}

	public String getFileId() {
		return fileId;
	}

	public String getFileURL() throws Exception {
		return "";
//		return FileSystemManage.getFileURL(fileId);
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

}
