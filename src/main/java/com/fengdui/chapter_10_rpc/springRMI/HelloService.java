package com.fengdui.chapter_10_rpc.springRMI;

import org.springframework.stereotype.Service;

/**
 * @author FD
 * @date 2017/1/20
 */

public interface HelloService {
    public String sayHello(String name);
    public String sayYourAge(int age);
}
