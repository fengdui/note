package com.fengdui.oa.business.app.entity;

import com.xh.market.framework.orm.EntityExtend;
import com.xh.market.framework.orm.TagLogicDelete;

/**
 * @author fd
 * @date 2015年10月19日
 * @desc AppUpdateInfo.java
 */
@TagLogicDelete
public class AppUpdateInfo extends EntityExtend{
	
	private static final long serialVersionUID = -5970068157567804382L;
	private static final Integer STRATEGY_MUST = 1;//必须更新
	private static final Integer STRATEGY_SHOULD = 2;//建议更新
	private Integer update_strategy = STRATEGY_SHOULD;//更新策略
	private Integer version_st;
	private Integer version_ed;
	//private Integer version_id;
	private String message;
	private Integer versionNum;
	private String updateFile;
	public Integer getUpdate_strategy() {
		return update_strategy;
	}
	public void setUpdate_strategy(Integer update_strategy) {
		this.update_strategy = update_strategy;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getVersion_st() {
		return version_st;
	}
	public void setVersion_st(Integer version_st) {
		this.version_st = version_st;
	}
	public Integer getVersion_ed() {
		return version_ed;
	}
	public void setVersion_ed(Integer version_ed) {
		this.version_ed = version_ed;
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
