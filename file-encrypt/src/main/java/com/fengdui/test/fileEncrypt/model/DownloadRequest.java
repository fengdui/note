package com.fengdui.test.fileEncrypt.model;

import java.io.Serializable;

/**
 * @author FD
 * @date 2016/12/30
 */
public class DownloadRequest implements Serializable{

    private int fileId;
    private int userId;
    private int start;
    private String requestId;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
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
