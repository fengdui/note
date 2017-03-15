package com.fengdui.extjs.pojo;

import java.io.Serializable;

/**
 * @author FD
 * @date 2017/3/15
 */
public class Role implements Serializable{
    private String role;
    private String description;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
