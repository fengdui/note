package com.zheyue.encrypt.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
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

    public static final String KET_ALGORITHM = "AES";

    public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    private Key toKey(byte[] key) {
        SecretKey secretKey = new SecretKeySpec(key, KET_ALGORITHM);
        return secretKey;
    }

    public byte[] decrypt(byte[] data, byte[] key) throws Exception{

        Key k = toKey(key);
//        Cipher.getInstance(CIPHER_ALGORITHM, "BC");
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, k);
        return cipher.doFinal(data);
    }

    @Async
    public byte[] encrypt(byte[] data, byte[] key) throws Exception{
        Key k = toKey(key);
//        Cipher.getInstance(CIPHER_ALGORITHM, "BC");
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, k);
        return cipher.doFinal(data);
    }
    @Async
    public void executeTask() {
        try {
            int id = count.getAndIncrement();
            long startTime = System.currentTimeMillis();
            System.out.println("执行任务 "+ id +" 开始时间 "+System.currentTimeMillis());
            byte[] bytes = new byte[1024*1024*100];

            Arrays.fill(bytes, (byte)6);
            encrypt(bytes, key.getBytes());

            System.out.println("结束任务 "+ id +" 耗时 "+ (System.currentTimeMillis()-startTime));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        System.out.println(Runtime.getRuntime().maxMemory()/1024/1024);
//        System.out.println(Runtime.getRuntime().freeMemory()/1024/1024);
//        System.out.println(Runtime.getRuntime().totalMemory()/1024/1024);
//
//        TaskService taskService = new TaskService();
//        for (int i = 0; i < 5; i++) {
//
//            taskService.executeTask();
//        }
//    }
}
