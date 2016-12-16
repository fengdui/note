package com.zheyue.encrypt.controller;

import com.zheyue.encrypt.service.MaterialService;
import com.zheyue.encrypt.service.StsService;
import com.zheyue.encrypt.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author FD
 * @date 2016/11/28
 */
@Controller
public class EncryptController {

    Logger LOGGER = LoggerFactory.getLogger(EncryptController.class);

    @Autowired
    private StsService stsService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private MaterialService materialService;


    public static final String publicKey = "FD";


    @RequestMapping("hello")
    public ModelAndView hello(HttpServletRequest request, ModelAndView mv) {
        mv.setViewName("hello");
        return mv;
    }

    @RequestMapping("publickey")
    @ResponseBody
    public String publicKey(HttpServletRequest request) {

        taskService.executeTask();
        System.out.println("fd");
        return publicKey;
    }

    @RequestMapping("status")
    public void status(HttpServletRequest request) {

    }

    @RequestMapping("encrypt")
    @ResponseBody
    public String encrypt(HttpServletRequest request) {
//        redisTemplate.convertAndSend("fd", "fd");
//        System.out.println("fd1");
//        String path =  materialService.getPathById(2);
//        if (StringUtils.isEmpty(path)) {
//            return "文件不存在";
//        }
        try {
            taskService.executeTask();
            return "加密成功";
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("XX");
        }
        return "加密失败";
    }
    //http://localhost:8080/stsCredentials/fd/fd
    @RequestMapping("stsCredentials/{userId}/{userName}")
    @ResponseBody
    public Object getStsCredentials(HttpServletRequest request, @PathVariable String userId, @PathVariable String userName) {
        try {
            return stsService.getStsCredentials(userId, userName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "获取sts失败";
    }
//    public static void main(String[] args) {
//        EncryptController encryptController = new EncryptController();
//        for (int i = 0; i < 10; i++)
//        encryptController.taskService.executeTask();
//    }
}
