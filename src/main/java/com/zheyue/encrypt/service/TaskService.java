package com.zheyue.encrypt.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author FD
 * @date 2016/11/30
 */

@Service
public class TaskService {

    Logger LOGGER = LoggerFactory.getLogger(TaskService.class);

    @Async
    public void executeTask() {
        try {
            Thread.sleep(3000);
            System.out.println("执行任务"+ Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
