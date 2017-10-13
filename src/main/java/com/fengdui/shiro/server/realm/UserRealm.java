package com.fengdui.shiro.server.realm;

import com.zheyue.authserver.entity.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * Created by fd on 2016/10/13.
 */
public class UserRealm extends AuthorizingRealm {

    private PasswordService passwordService;

    public PasswordService getPasswordService() {
        return passwordService;
    }

    public void setPasswordService(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    //授权
    @Override

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        return authorizationInfo;
    }
    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        try {

            User user = new User();
            user.setUserName("fd");
            user.setPassword("fd");
            return getAuthenticationInfoWithPasswordService(user);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private AuthenticationInfo getAuthenticationInfoWithPasswordService(User user) {
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getUserName(), passwordService.encryptPassword(user.getPassword()), getName());
        return authenticationInfo;
    }
}
