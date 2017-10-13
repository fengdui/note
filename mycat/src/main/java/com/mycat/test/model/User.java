package com.mycat.test.model;

public class User {
    private Long userId;
    private String name;
    private Long roleId;

    public void setName(String name) {//
        this.name= name;
    }
    public String getName() {
        return name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
