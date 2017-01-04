package com.zheyue.encrypt.model;

import java.io.Serializable;

/**
 * @author FD
 * @date 2016/12/30
 */
public class DownloadRequest implements Serializable{

    private int id;
    private int userId;
    private int start;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }
}
