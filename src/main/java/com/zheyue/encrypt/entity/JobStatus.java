package com.zheyue.encrypt.entity;

import com.zheyue.encrypt.utils.Status;

/**
 * @author FD
 * @date 2016/11/29
 */
public class JobStatus {

    private String filePath;
    private int jobCode = Status.UNSUBMIT.getCode();
    private String jobMessage = Status.UNSUBMIT.getMsg();

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getJobCode() {
        return jobCode;
    }

    public void setJobCode(int jobCode) {
        this.jobCode = jobCode;
    }

    public String getJobMessage() {
        return jobMessage;
    }

    public void setJobMessage(String jobMessage) {
        this.jobMessage = jobMessage;
    }
}
