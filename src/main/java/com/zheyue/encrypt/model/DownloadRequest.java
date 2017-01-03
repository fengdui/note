package com.zheyue.encrypt.model;

import java.io.Serializable;

/**
 * @author FD
 * @date 2016/12/30
 */
public class DownloadRequest implements Serializable{

    private int requestId;
    private int fileNum;
    private int[] fileId;

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getFileNum() {
        return fileNum;
    }

    public void setFileNum(int fileNum) {
        this.fileNum = fileNum;
    }

    public int[] getFileId() {
        return fileId;
    }

    public void setFileId(int[] fileId) {
        this.fileId = fileId;
    }

}
