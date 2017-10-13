package com.fengdui.oa.business.sys.entity;

import com.fengdui.oa.framework.orm.EntityExtend;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Wander.Zeng
 * @data 2015-8-10 下午7:00:25
 * @desc RoleMenuRef.java
 */
public class RoleMenuRef extends EntityExtend {

	private static final long serialVersionUID = 1247757409118103623L;

	private Integer roleId;
	private Integer menuId;

	public RoleMenuRef() {

	}

	public RoleMenuRef(Integer roleId, Integer menuId) {
		this.roleId = roleId;
		this.menuId = menuId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
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
