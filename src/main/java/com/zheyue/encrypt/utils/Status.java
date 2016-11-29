package com.zheyue.encrypt.utils;

/**
 * @author FD
 * @date 2016/11/29
 */
public enum Status {

    UNSUBMIT(0, "任务未提交"),
    RUNNING(1, "运行中"),
    SUCCESS(2, "成功"),
    FAILURE(3, "失败");

    private int code;
    private String msg;

    Status(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
