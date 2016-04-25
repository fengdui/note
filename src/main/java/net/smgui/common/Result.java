package net.smgui.common;

import lombok.Data;

@Data
public class Result {
    private boolean result;
    private String msg;
    private Object obj;

    public Result(boolean result, String msg) {
        this.result = result;
        this.msg = msg;
    }

    public Result(boolean result, String msg, Object obj) {
        this.result = result;
        this.msg = msg;
        this.obj = obj;
    }
}
