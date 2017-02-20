package com.fengdui.oa.business.sys.entity;

import com.fengdui.oa.framework.orm.EntityExtend;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Wander.Zeng
 * @data 2015-8-25 下午5:45:14
 * @desc RolegroupMenuRef.java
 */
public class RolegroupMenuRef extends EntityExtend {

	private static final long serialVersionUID = 40183028914545521L;

	private Integer rolegroupId;
	private Integer menuId;

	public RolegroupMenuRef() {

	}

	public RolegroupMenuRef(Integer rolegroupId, Integer menuId) {
		this.rolegroupId = rolegroupId;
		this.menuId = menuId;
	}

	public Integer getRolegroupId() {
		return rolegroupId;
	}

	public void setRolegroupId(Integer rolegroupId) {
		this.rolegroupId = rolegroupId;
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
