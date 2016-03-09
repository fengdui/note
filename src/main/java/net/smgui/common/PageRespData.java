package net.smgui.common;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * PageRespData
 *
 * @author FD
 * @date 2016/3/10 0010
 */
public class PageRespData {
    private boolean result;
    private String msg;
    private Object obj;

    public PageRespData(boolean result, String msg) {
        this.result = result;
        if (null != msg && msg.length() > 1000) {
            msg = msg.substring(0, 1000);
        }
        this.msg = msg;
    }

    public PageRespData(boolean result, String msg, Object obj) {
        this.result = result;
        this.msg = msg;
        this.obj = obj;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
