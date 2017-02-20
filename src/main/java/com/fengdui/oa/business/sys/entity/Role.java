package com.fengdui.oa.business.sys.entity;

import com.xh.market.framework.orm.EntityExtend;
import com.xh.market.framework.orm.TagLogicDelete;
import com.xh.market.framework.orm.TagUnionColumn;
import com.xh.market.framework.orm.TagUniqueColumn;
import com.xh.market.framework.util.string.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Wander.Zeng
 * @data 2015-8-3 下午2:52:36
 * @desc Role.java
 */
@TagLogicDelete
@TagUnionColumn(fieldNameUnion = { "name", "level#rolegroupId" })
public class Role extends EntityExtend {

	private static final long serialVersionUID = 5385857211409083825L;

	public static final int ID_ROOT = 0;
	public static final short LEVEL_MAX = Short.MAX_VALUE;

	@TagUniqueColumn(colName = "name", colDesc = "角色名称")
	private String name;
	@TagUniqueColumn(colName = "level", colDesc = "角色等级")
	private Short level;
	@TagUniqueColumn(colName = "rolegroup_id", colDesc = "角色组")
	private Integer rolegroupId;
	private String description;

	private String rolegroupName;
	private int countUser;
	private String menuListStr;

	public Role() {

	}

	public Role(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Short getLevel() {
		return level;
	}

	public void setLevel(Short level) {
		this.level = level;
	}

	public Integer getRolegroupId() {
		return rolegroupId;
	}

	public void setRolegroupId(Integer rolegroupId) {
		this.rolegroupId = rolegroupId;
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

	public String getRolegroupName() {
		return rolegroupName;
	}

	public void setRolegroupName(String rolegroupName) {
		this.rolegroupName = rolegroupName;
	}

	public int getCountUser() {
		return countUser;
	}

	public void setCountUser(int countUser) {
		this.countUser = countUser;
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
