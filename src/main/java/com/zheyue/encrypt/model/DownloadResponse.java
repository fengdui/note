package com.zheyue.encrypt.model;

import java.io.Serializable;

/**
 * @author FD
 * @date 2016/12/30
 */
public class DownloadResponse implements Serializable{

    //文件id
    private int id;
    //数据 每一块最多64kb
    private byte[] data;
    //字节数
    private int length;
    //是否结束
    private boolean isEOF;
    //错误码
    private String code;

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


    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
