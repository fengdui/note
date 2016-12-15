package com.zheyue.encrypt.controller;

import com.zheyue.encrypt.service.EncyptService;
import com.zheyue.encrypt.service.MaterialService;
import com.zheyue.encrypt.service.StsService;
import com.zheyue.encrypt.service.TaskService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

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
    @Autowired
    private RedisTemplate redisTemplate;


    public static final String publicKey = "FD";

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
    @RequestMapping("stsCredentials/{userId}/{userName}")
    public Object getStsCredentials(HttpServletRequest request, @PathVariable String userId, @PathVariable String userName) {
        try {
            return stsService.getStsCredentials(userId, userName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    public static void main(String[] args) {
//        EncryptController encryptController = new EncryptController();
//        for (int i = 0; i < 10; i++)
//        encryptController.taskService.executeTask();
//    }
}
