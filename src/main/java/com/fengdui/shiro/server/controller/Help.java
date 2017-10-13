package com.fengdui.shiro.server.controller;

import com.zheyue.authserver.remote.RemoteService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by fd on 2016/10/17.
 */
public class Help {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-config.xml");
        RemoteService remoteService = (RemoteService)applicationContext.getBean("remoteService");
        remoteService.getName();
    }
}
