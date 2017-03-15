package com.fengdui.extjs.pojo;

import java.io.Serializable;

/**
 * @author FD
 * @date 2017/3/15
 */
public class Ip implements Serializable{
    private String ip;
    private String address;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
