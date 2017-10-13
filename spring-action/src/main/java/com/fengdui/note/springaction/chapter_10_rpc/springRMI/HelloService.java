package com.fengdui.note.springaction.chapter_10_rpc.springRMI;

/**
 * @author FD
 * @date 2017/1/20
 */

public interface HelloService {
    public String sayHello(String name);
    public String sayYourAge(int age);
}
