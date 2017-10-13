package com.fengdui.test.xhoa.business.auth.entity;

import com.fengdui.test.xhoa.framework.orm.EntityExtend;
import com.fengdui.test.xhoa.framework.orm.TagLogicDelete;
import com.fengdui.test.xhoa.framework.orm.TagUniqueColumn;
import com.fengdui.test.xhoa.framework.util.encrypt.EncryptUtil;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Wander.Zeng
 * @data 2015-7-1 下午3:51:17
 * @desc User.java
 */
@TagLogicDelete
public class User extends EntityExtend {

	private static final long serialVersionUID = 7525370345073214852L;

	public static final int ID_ROOT = 0;
	public static final String DEFAULT_PASSWORD = "123456";

	private int parentId;
	@TagUniqueColumn(colName = "loginname", colDesc = "登录名称")
	private String loginname;
	private String username;
	private String password;
	private Integer roleId;

	private String passwordNew;
	private String roleName;
	private short roleLevel;
	private Integer rolegroupId;
	private String menuListStr;

	public static String encryptPWD(String pwd) {
		return EncryptUtil.encryptBySha256(pwd);
	}

	public boolean isRoot() {
		if (null != getId()) {
			return getId() == ID_ROOT;
		}
		return false;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public String getPasswordEncrypt() {
		return User.encryptPWD(password);
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getPasswordNew() {
		return passwordNew;
	}

	public void setPasswordNew(String passwordNew) {
		this.passwordNew = passwordNew;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public short getRoleLevel() {
		return roleLevel;
	}

	public void setRoleLevel(short roleLevel) {
		this.roleLevel = roleLevel;
	}

	public Integer getRolegroupId() {
		return rolegroupId;
	}

	public void setRolegroupId(Integer rolegroupId) {
		this.rolegroupId = rolegroupId;
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
