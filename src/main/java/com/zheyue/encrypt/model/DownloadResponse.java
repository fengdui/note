package com.zheyue.encrypt.model;

import java.io.Serializable;

/**
 * @author FD
 * @date 2016/12/30
 */
public class DownloadResponse implements Serializable{

    private int requestId;
    //文件id
    private int id;
    //数据 每一块64kb
    private byte[] data;
    //是否加密
    private boolean isEncrypt;
    //字节数
    private int length;
    // x-y 代表第x文件的第y块
    private String progress;
    //错误码
    private String code;

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public boolean isEncrypt() {
        return isEncrypt;
    }

    public void setIsEncrypt(boolean isEncrypt) {
        this.isEncrypt = isEncrypt;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
