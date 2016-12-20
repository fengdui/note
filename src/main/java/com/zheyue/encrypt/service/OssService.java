package com.zheyue.encrypt.service;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.AppendObjectRequest;
import com.aliyun.oss.model.AppendObjectResult;
import com.aliyun.oss.model.OSSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author FD
 * @date 2016/12/14
 */
@Service
public class OssService {

    @Autowired
    private OSSClient client;

    @Value("${oss.endpoint}")
    private String endpoint;

    @Value("${oss.bucketName}")
    private String bucketName;

    public void putObject() {
    }

    public void getObject() {

    }

    public String getAndEncryptAndPut(String key, String userName) throws IOException {
        InputStream in = null;
        String newKey = UUID.randomUUID().toString();
        try {
            OSSObject ossObject = client.getObject(bucketName, key);
            long startTime = System.currentTimeMillis();
            in = ossObject.getObjectContent();
            byte[] bytes = new byte[1024*1024];
            int len = 0;
            long position = 0;
            int sum = 0;
            int cnt = 0;
            while ((len = in.read(bytes)) != -1) {
                sum += len;
                cnt++;
//                ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes, 0, len);
//                AppendObjectRequest request = new AppendObjectRequest(bucketName, newKey, inputStream);
//                request.setPosition(position);
//                AppendObjectResult appendObjectResult = client.appendObject(request);
//                position = appendObjectResult.getNextPosition();
            }
            System.out.println("加密完成 大小 "+sum/1024.0+"KB 耗时 "+ (System.currentTimeMillis()-startTime) + " " + cnt+" 次大小 "+len);
//            System.out.println("加密完成 大小 "+sum/1024.0/1024+"MB 耗时 "+ (System.currentTimeMillis()-startTime)+"  "+sum);
            return newKey;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

//    public static void main(String[] args) {
//        OSSClient ossClient = new OSSClient("http://oss-cn-hangzhou.aliyuncs.com", "LTAIwKGyu3xB7S6a", "9dbcFotQhlzwTa8DkYhbmDdP5BO7Ow");
////        ossClient.getObject("zykj-oss-1", "fd.pdf");
//        ossClient.putObject("zykj-oss-1", "fd2", new File("C:\\Users\\FD\\Desktop\\OSS.sql"));
//        ossClient.shutdown();
//    }
}
