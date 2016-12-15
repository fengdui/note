package com.zheyue.encrypt.service;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author FD
 * @date 2016/12/14
 */
public class OssService {

    @Autowired
    private OSSClient client;

    @Value("${oss.endpoint}")
    private String endpoint;

    public void putObject() {
    }

    public void getObject() {

    }

    public void getAndEncryptAndPut(String key, String userName) throws IOException {
        OSSObject ossObject = client.getObject(endpoint, key);
        InputStream in = ossObject.getObjectContent();
        byte[] bytes = new byte[1024];
        int len = 0;
        while ((len = in.read(bytes)) != -1) {

        }
    }
}
