package com.zheyue.encrypt.service;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author FD
 * @date 2016/12/14
 */
public class OssService {

    @Autowired
    private OSSClient client;

    public void putObject() {
    }
}
