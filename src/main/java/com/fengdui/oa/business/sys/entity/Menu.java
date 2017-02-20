package com.fengdui.oa.business.sys.entity;

import com.xh.market.framework.orm.EntityExtend;
import com.xh.market.framework.orm.TagLogicDelete;
import com.xh.market.framework.orm.TagUniqueColumn;
import com.xh.market.framework.util.string.StringUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * @author Wander.Zeng
 * @data 2015-7-17 上午11:17:52
 * @desc Menu.java
 */
@TagLogicDelete
public class Menu extends EntityExtend {

	private static final long serialVersionUID = -7690237479034971681L;

	public static final short LEVEL_MAX = 3;
	public static final short LEVEL_ROOT = 0;
	public static final String[] ICON_DEFAULT = { "fa fa-home", "fa fa-align-right", "fa fa-circle", "fa fa-circle" };
	public static final String[] ICON_LIST = { "glyphicon glyphicon-home", "glyphicon glyphicon-tasks", "fa fa-pencil-square-o", "glyphicon glyphicon-paperclip", "glyphicon glyphicon-link",
			"fa fa-th", "fa fa-circle", "fa fa-align-right", "fa fa-th-large", "fa fa-paste" };
	private static final int ID_MENU_MANAGE = 2;
	private static final int ID_ORG_MANAGE = 3;
	private static final int ID_ROLEGROUP_MANAGE = 4;
	private static final int ID_ROLE_MANAGE = 5;
	public static final int[] MENU_FILER = { ID_MENU_MANAGE, ID_ORG_MANAGE, ID_ROLEGROUP_MANAGE, ID_ROLE_MANAGE };

	private Integer parentId;
	private String icon;
	@TagUniqueColumn(colName = "name", colDesc = "菜单名称")
	private String name;
	private String url;
	private Short seq;
	private Short level;
	private String description;

	private int subMenuCount;
	private List<Menu> subMenuList;

	private String key;
	private boolean changeRef;
	private String refListStr;

	public Menu() {

	}

	public Menu(int id) {
		this.id = id;
	}

	public Menu(Menu menuRef) {
		this.id = menuRef.getId();
		this.parentId = menuRef.getParentId();
		this.icon = menuRef.getIcon();
		this.name = menuRef.getName();
		this.url = menuRef.getUrl();
		this.seq = menuRef.getSeq();
		this.level = menuRef.getLevel();
		this.description = menuRef.getDescription();
		this.key = menuRef.getKey();
	}

	public boolean isLimit() {
		if (isNew()) {
			return false;
		} else {
			return ArrayUtils.contains(MENU_FILER, getId());
		}
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getIcon() {
		if (StringUtils.isBlank(icon) && null != getLevel()) {
			return ICON_DEFAULT[getLevel()];
		}
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		if (StringUtils.isBlank(url)) {
			return null;
		}
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Short getSeq() {
		return seq;
	}

	public void setSeq(Short seq) {
		this.seq = seq;
	}

	public Short getLevel() {
		return level;
	}

	public void setLevel(Short level) {
		this.level = level;
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

	public int getSubMenuCount() {
		return subMenuCount;
	}

	public void setSubMenuCount(int subMenuCount) {
		this.subMenuCount = subMenuCount;
	}

	public List<Menu> getSubMenuList() {
		return subMenuList;
	}

	public void setSubMenuList(List<Menu> subMenuList) {
		this.subMenuList = subMenuList;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public boolean isChangeRef() {
		return changeRef;
	}

	public void setChangeRef(boolean changeRef) {
		this.changeRef = changeRef;
	}

	public String getRefListStr() {
		return refListStr;
	}

	public void setRefListStr(String refListStr) {
		this.refListStr = refListStr;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Menu other = (Menu) obj;
		return this.getId().equals(other.getId());
	}

}
