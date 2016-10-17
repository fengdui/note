package com.zheyue.authserver.controller;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
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

    public static void main(String[] args) throws InterruptedException {
        int n = 23 - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        System.out.println(0x7fffffff);
        new Hello().wait();
        GenericBeanDefinition a = new GenericBeanDefinition();
    }
}
