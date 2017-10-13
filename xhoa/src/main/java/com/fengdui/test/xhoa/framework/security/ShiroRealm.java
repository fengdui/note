package com.fengdui.test.xhoa.framework.security;

import com.xh.market.business.auth.entity.User;
import com.xh.market.business.auth.service.UserService;
import com.xh.market.framework.constant.ConstantSession;
import com.xh.market.framework.util.string.StringUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wander.Zeng
 * @data 2015-7-9 下午3:23:08
 * @desc ShiroRealm.java
 */
public class ShiroRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;

	/**
	 * 在登录认证时被回调
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();
		String password = new String((char[]) token.getCredentials());
		String result = userService.checkUser(username, password);
		if (null == result) {
			return new SimpleAuthenticationInfo(username, password, getName());
		} else {
			throw new AuthenticationException(result);
		}
	}

	/**
	 * 在权限认证也就是访问控制时被回调
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// String loginname = (String) principals.getPrimaryPrincipal();
		// User user = userService.getUser(loginname);
		User user = ConstantSession.getInstance().getUser();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.addRole(user.getRoleId().toString());
		authorizationInfo.setStringPermissions(StringUtil.convertStringToStringSet(user.getMenuListStr(), ","));
		return authorizationInfo;
	}

	@Override
	protected void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}

	@Override
	protected void clearCachedAuthenticationInfo(PrincipalCollection principals) {
		super.clearCachedAuthenticationInfo(principals);
	}

	@Override
	protected void clearCache(PrincipalCollection principals) {
		super.clearCache(principals);
	}

	public void clearAllCachedAuthorizationInfo() {
		getAuthorizationCache().clear();
	}

	public void clearAllCachedAuthenticationInfo() {
		getAuthenticationCache().clear();
	}

	public void clearAllCache() {
		clearAllCachedAuthenticationInfo();
		clearAllCachedAuthorizationInfo();
	}

}
