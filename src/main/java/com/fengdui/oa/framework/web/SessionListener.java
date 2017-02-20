package com.fengdui.oa.framework.web;


import com.fengdui.oa.framework.constant.ConstantSession;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author Wander.Zeng
 * @data 2015-7-16 下午4:34:52
 * @desc SessionListener.java
 */
public class SessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		// session 超时时把对应的在线用户也移除掉
		InitService initService = SpringContextHolder.getBean("initService", InitService.class);
		String loginname = (String) se.getSession().getAttribute(ConstantSession.KEY_LOGINNAME);
		initService.getManagerServer().userSessionTimeout(loginname);
	}

}
