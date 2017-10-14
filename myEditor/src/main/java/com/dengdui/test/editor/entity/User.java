package com.dengdui.test.editor.entity;

import lombok.Data;

/**
 * User
 *
 * @author FD
 * @date 2016/2/14 0014
 */
@Data
public class User {

    private int id;
    private String username;
    private String password;

    public User(){}

    public User(int id){
        this.id = id;
    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

}