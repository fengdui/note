package com.fengdui.shiro.server.controller;

import com.zheyue.authserver.service.UserService;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by fd on 2016/9/24.
 */
@Controller
@RequestMapping("/")
public class Hello {
    @RequestMapping("/home")
    public void hello() {
        System.out.println("x");
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-config.xml");
        UserService userService = (UserService)applicationContext.getBean("userService");
        try {
            userService.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {

        }
    }
}
