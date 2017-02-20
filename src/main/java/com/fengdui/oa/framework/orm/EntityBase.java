package com.fengdui.oa.framework.orm;

import com.xh.market.framework.constant.ConstantSession;
import com.xh.market.framework.util.date.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Wander.Zeng
 * @data 2015-7-1 下午4:24:53
 * @desc EntityBase.java
 */
@SuppressWarnings("serial")
public class EntityBase implements Serializable {

	/** id */
	protected Integer id;

	/** 创建者(用户登录名) */
	protected String createUser;

	/** 创建时间(默认当前时间) */
	protected Date createTime;

	/** 修改者(用户登录名) */
	protected String modifyUser;

	/** 修改时间 */
	protected Date modifyTime;

	/** 逻辑删除标识 */
	protected boolean deleted = false;

	public boolean isNew() {
		return null == id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCreateUser() {
		// 如果创建者为空则取当前登录名
		if (StringUtils.isBlank(createUser)) {
			return ConstantSession.getInstance().getLoginname();
		}
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getModifyUser() {
		// 如果修改者为空则取当前登录名
		if (StringUtils.isBlank(modifyUser)) {
			return ConstantSession.getInstance().getLoginname();
		}
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Date getModifyTime() {
		if (null == modifyTime) {
			return Calendar.getInstance().getTime();
		}
		return modifyTime;
	}

	public String getModifyTimeStr() {
		return DateUtil.convertDate2String(getModifyTime());
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

}
