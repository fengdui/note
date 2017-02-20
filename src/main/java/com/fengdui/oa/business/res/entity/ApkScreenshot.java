package com.fengdui.oa.business.res.entity;

import com.xh.market.framework.filesystem.FileSystemManage;
import com.xh.market.framework.orm.EntityExtend;
import com.xh.market.framework.orm.TagUniqueColumn;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Wander.Zeng
 * @data 2015-9-22 上午11:19:22
 * @desc ApkScreenshot.java
 */
public class ApkScreenshot extends EntityExtend {

	private static final long serialVersionUID = -8797259758706104817L;

	@TagUniqueColumn(colName = "apk_id", colDesc = "apk id")
	private Integer apkId;
	private String fileId;
	@TagUniqueColumn(colName = "dpi_type", colDesc = "dpi类型")
	private Integer dpiType;
	@TagUniqueColumn(colName = "seq", colDesc = "顺序")
	private Integer seq;
	private Integer width;
	private Integer height;

	public ApkScreenshot() {

	}

	public ApkScreenshot(String fileId, Integer dpiType, Integer width, Integer height) {
		this.fileId = fileId;
		this.dpiType = dpiType;
		this.width = width;
		this.height = height;
	}

	public Integer getApkId() {
		return apkId;
	}

	public void setApkId(Integer apkId) {
		this.apkId = apkId;
	}

	public String getFileId() {
		return fileId;
	}

	public String getFileURL() throws Exception {
		return FileSystemManage.getFileURL(fileId);
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public Integer getDpiType() {
		return dpiType;
	}

	public void setDpiType(Integer dpiType) {
		this.dpiType = dpiType;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
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
