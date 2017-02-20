package com.fengdui.oa.business.app.entity;

import com.xh.market.framework.orm.EntityExtend;
import com.xh.market.framework.orm.TagLogicDelete;

/**
 * @author fd
 * @date 2015年10月22日
 * @desc AppVersionInfo.java
 */
@TagLogicDelete
public class AppVersionInfo extends EntityExtend{

	private static final long serialVersionUID = -5270322056928707208L;
	private String message;
	private Integer versionNum;
	private String updateFile;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getVersionNum() {
		return versionNum;
	}
	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}
	public String getUpdateFile() {
		return updateFile;
	}
	public void setUpdateFile(String updateFile) {
		this.updateFile = updateFile;
	}
}
