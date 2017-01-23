package com.fengdui.shiro.server.entity;

/**
 * Created by fd on 2016/10/13.
 */
public class User {
    private String userName;
    private String password;
    private String salt; //加密密码的盐

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
