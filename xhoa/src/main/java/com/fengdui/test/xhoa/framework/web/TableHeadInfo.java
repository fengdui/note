package com.fengdui.test.xhoa.framework.web;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Wander.Zeng
 * @data 2015-8-18 上午8:26:35
 * @desc TableHeadInfo.java
 */
public class TableHeadInfo {
	public static final String TYPE_CHECKBOX = "checkbox";
	public static final String TYPE_SORT = "sort";
	public static final String TYPE_SEL = "sel";

	private String css;
	private String nameShow;
	private String type;
	private String nameCol;
	private Object data;
	private Object defaultVal;

	public TableHeadInfo() {

	}

	public TableHeadInfo(String css, String nameShow) {
		this.css = css;
		this.nameShow = nameShow;
	}

	public TableHeadInfo(String css, String nameShow, String type) {
		this.css = css;
		this.nameShow = nameShow;
		this.type = type;
	}

	public TableHeadInfo(String css, String nameShow, String type, String nameCol) {
		this.css = css;
		this.nameShow = nameShow;
		this.type = type;
		this.nameCol = nameCol;
	}

	public TableHeadInfo(String css, String nameShow, String type, String nameCol, Object data) {
		this.css = css;
		this.nameShow = nameShow;
		this.type = type;
		this.nameCol = nameCol;
		this.data = data;
	}

	public TableHeadInfo(String css, String nameShow, String type, String nameCol, Object data, Object defaultVal) {
		this.css = css;
		this.nameShow = nameShow;
		this.type = type;
		this.nameCol = nameCol;
		this.data = data;
		this.defaultVal = defaultVal;
	}

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public String getNameShow() {
		return nameShow;
	}

	public void setNameShow(String nameShow) {
		this.nameShow = nameShow;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNameCol() {
		return nameCol;
	}

	public void setNameCol(String nameCol) {
		this.nameCol = nameCol;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Object getDefaultVal() {
		return defaultVal;
	}

	public void setDefaultVal(Object defaultVal) {
		this.defaultVal = defaultVal;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

}
