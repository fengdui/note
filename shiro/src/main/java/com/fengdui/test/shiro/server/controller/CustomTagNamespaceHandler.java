package com.fengdui.test.shiro.server.controller;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by fd on 2016/10/10.
 */
public class CustomTagNamespaceHandler extends NamespaceHandlerSupport {

    public void init() {
        registerBeanDefinitionParser("user", new UserBeanDefinitionParser());
    }
}
