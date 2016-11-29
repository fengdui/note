package com.zheyue.encrypt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author FD
 * @date 2016/11/28
 */
@Controller
public class EncryptController {

    @RequestMapping("publicKey")
    public ModelAndView publicKey(HttpServletRequest request, ModelAndView mv) {

        System.out.println("fd");
        return mv;
    }

    @RequestMapping("status")
    public void status(HttpServletRequest request) {

    }

    @RequestMapping("encrypt")
    public void encrypt(HttpServletRequest request) {

    }

}
