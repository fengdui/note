package com.zheyue.encrypt.config;

import com.aliyun.oss.OSSClient;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author FD
 * @date 2016/12/14
 */
@Configuration
public class AliyunConfig {

//    @Value("${oss.endpoint}")
//    private String endpoint;
//
//    @Value("${oss.accessKeyId}")
//    private String accessKeyId;
//
//    @Value("${oss.accessKeySecret}")
//    private String accessKeySecret;
//
//    @Value("${oss.bucketName}")
//    private String bucketName;
//
//    @Bean("ossClient")
//    public OSSClient ossClient() {
//        return new OSSClient(endpoint, accessKeyId, accessKeySecret);
//    }

    // 目前只有"cn-hangzhou"这个region可用, 不要使用填写其他region的值
    @Value("${sts.region_cn_hangzhou}")
    public String region_cn_hangzhou;

    @Value("${ram.accessKeyId}")
    public String accessKeyId;

    @Value("${ram.accessKeySecret}")
    public String accessKeySecret;

    public DefaultAcsClient acsClient() {
        IClientProfile profile = DefaultProfile.getProfile(region_cn_hangzhou, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
    }

}
