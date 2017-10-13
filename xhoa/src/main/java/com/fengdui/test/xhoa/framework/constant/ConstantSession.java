package com.fengdui.oa.framework.constant;

import com.fengdui.oa.business.auth.entity.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author Wander.Zeng
 * @data 2015-7-1 下午4:37:11
 * @desc ConstantSession.java
 */
public class ConstantSession {

	private static ConstantSession instance;

	public static final String KEY_USER = "s_user";
	public static final String KEY_USER_ISROOT = "s_user_isroot";
	public static final String KEY_USER_ID = "s_user_id";
	public static final String KEY_USER_ROLEGROUPID = "s_user_rolegroupid";
	public static final String KEY_USER_NAME = "s_user_name";
	public static final String KEY_LOGINNAME = "s_loginname";
	public static final String KEY_ONLINE_USER_MANAGER_SERVER_PORT = "s_online_user_manager_server_port";

	private ConstantSession() {

	}

	public static ConstantSession getInstance() {
		if (null == instance) {
			instance = new ConstantSession();
		}
		return instance;
	}

	private HttpSession getSession() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
	}

	/** 获取用户 */
	public User getUser() {
		return (User) getSession().getAttribute(KEY_USER);
	}

	public void setUser(User user) {
		getSession().setAttribute(KEY_USER, user);
		getSession().setAttribute(KEY_USER_ISROOT, user.isRoot());
		getSession().setAttribute(KEY_USER_ID, user.getId());
		getSession().setAttribute(KEY_USER_ROLEGROUPID, user.getRolegroupId());
		getSession().setAttribute(KEY_USER_NAME, user.getUsername());
	}

	/** 获取用户登录名 */
	public String getLoginname() {
		return (String) getSession().getAttribute(KEY_LOGINNAME);
	}

	/** 获取空间ID */
	public Integer getSpaceId() {
		return 0;
	}

}
