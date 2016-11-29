package com.zheyue.encrypt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

/**
 * @author FD
 * @date 2016/11/28
 */
@Controller
public class EncryptController {

    @Autowired
    DataSource dataSource;

    public static final String publicKey = "FD";

    @RequestMapping("publickey")
    @ResponseBody
    public String publicKey(HttpServletRequest request) {

        System.out.println("fd");
        return publicKey;
    }

    @RequestMapping("status")
    public void status(HttpServletRequest request) {

    }

    @RequestMapping("encrypt")
    public void encrypt(HttpServletRequest request) {

    }

}
