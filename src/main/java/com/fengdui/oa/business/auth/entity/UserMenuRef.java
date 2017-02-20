package com.fengdui.oa.business.auth.entity;

import com.fengdui.oa.framework.orm.EntityExtend;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Wander.Zeng
 * @data 2015-8-18 下午5:09:41
 * @desc UserMenuRef.java
 */
public class UserMenuRef extends EntityExtend {

	private static final long serialVersionUID = 5321272763230845855L;

	private Integer userId;
	private Integer menuId;

	public UserMenuRef() {

	}

	public UserMenuRef(Integer userId, Integer menuId) {
		this.userId = userId;
		this.menuId = menuId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

}
