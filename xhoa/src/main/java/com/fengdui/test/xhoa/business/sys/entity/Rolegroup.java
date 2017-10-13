package com.fengdui.test.xhoa.business.sys.entity;

import com.fengdui.test.xhoa.framework.orm.EntityExtend;
import com.fengdui.test.xhoa.framework.orm.TagLogicDelete;
import com.fengdui.test.xhoa.framework.orm.TagUniqueColumn;
import com.fengdui.test.xhoa.framework.util.string.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Wander.Zeng
 * @data 2015-8-25 下午4:06:52
 * @desc Rolegroup.java
 */
@TagLogicDelete
public class Rolegroup extends EntityExtend {

	private static final long serialVersionUID = -5855525647405853162L;

	public static final int ID_ROOT_GROUP = 0;

	@TagUniqueColumn(colName = "name", colDesc = "角色组名称")
	private String name;
	private String description;

	private int countRole;
	private String menuListStr;

	public Rolegroup() {

	}

	public Rolegroup(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		if (StringUtils.isBlank(description)) {
			return null;
		}
		return StringUtil.subString(description, 256);
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCountRole() {
		return countRole;
	}

	public void setCountRole(int countRole) {
		this.countRole = countRole;
	}

	public String getMenuListStr() {
		return menuListStr;
	}

	public void setMenuListStr(String menuListStr) {
		this.menuListStr = menuListStr;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

}
