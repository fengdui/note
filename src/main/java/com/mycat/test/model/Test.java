package com.mycat.test.model;

/**
* @ClassName: Test
* @Description: test
* @author: suxl
* @date: 2015-01-19 16:40:14
*/
public class Test {
    private Long id;
    private String name;
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}
