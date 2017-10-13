package com.fengdui.test.dubbo.server.service;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * @author FD
 * @date 2016/12/20
 */
@Service(version="1.0.0")
public class DubboDemoServiceImpl implements IDubboDemoService{

    @Override
    public String sayHello(String name) {
        return "hello " + name;
    }

    @Override
    public String sayYourAge(int age) {
        return null;
    }

}