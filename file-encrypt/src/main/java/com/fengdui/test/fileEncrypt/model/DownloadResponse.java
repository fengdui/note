package com.fengdui.test.fileEncrypt.model;

import java.io.Serializable;

/**
 * @author FD
 * @date 2016/12/30
 */
public class DownloadResponse implements Serializable{

    private int fileId;
    private int userId;
    private String requestId;
    //数据 每一块最多64kb
    private byte[] data;
    //字节数
    private int length;
    //是否结束
    private boolean isEOF;
    //错误码
    private String code;

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

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isEOF() {
        return isEOF;
    }

    public void setIsEOF(boolean isEOF) {
        this.isEOF = isEOF;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
