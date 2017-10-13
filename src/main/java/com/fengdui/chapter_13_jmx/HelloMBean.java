package com.fengdui.chapter_13_jmx;

/**
 * HelloMBean
 *
 * @author FD
 * @date 2016/5/25
 */
public interface HelloMBean {
    public String getName();
    public void setName(String name);
    public void printHello();
}
