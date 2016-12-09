package com.zheyue.encrypt.service;

import com.zheyue.encrypt.coder.AESCoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author FD
 * @date 2016/11/30
 */

@Service
public class TaskService {

//    @Autowired
//    private AESCoder aesCoder;

    private String key = "ffffffffdddddddd";

    public static AtomicInteger count = new AtomicInteger(1);
    Logger LOGGER = LoggerFactory.getLogger(TaskService.class);

    @Async
    public void executeTask() {
        try {
            int id = count.getAndIncrement();
            long startTime = System.currentTimeMillis();
            System.out.println("执行任务 "+ id +" 开始时间 "+System.currentTimeMillis());
//            File file = new File("C:\\Users\\FD\\Desktop\\fd.pdf");
//            FileOutputStream
            byte[] bytes = new byte[1024*1024*100];
            Arrays.fill(bytes, (byte)6);
            for (int i = 0; i < 1; i++) {
                AESCoder.encrypt(bytes, key.getBytes());

            }
            System.out.println("结束任务 "+ id +" 耗时 "+ (System.currentTimeMillis()-startTime));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().maxMemory()/1024/1024);
        System.out.println(Runtime.getRuntime().freeMemory()/1024/1024);
        System.out.println(Runtime.getRuntime().totalMemory()/1024/1024);

//        TaskService taskService = new TaskService();
//        for (int i = 0; i < 1; i++) {
//
//            taskService.executeTask();
//        }
    }
}
