package com.zheyue.encrypt.service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author FD
 * @date 2016/12/15
 */

@Service
public class StsService {


//    {
//        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIwKGyu3xB7S6a", "9dbcFotQhlzwTa8DkYhbmDdP5BO7Ow");
//        this.client = new DefaultAcsClient(profile);
//    }
    @Autowired
    public DefaultAcsClient client;

    // 当前 STS API 版本
    @Value("${sts.api_version}")
    public String sts_api_version;

    @Value("${ram.roleArn}")
    public String roleArn;

    public String getRoleSessionName(String userId, String userName) throws Exception{
        return userId + "_" + userName;
    }


    public AssumeRoleResponse assumeRole(String roleSessionName, String policy) throws Exception {
        try {
            // 创建一个 AssumeRoleRequest 并设置请求参数
            final AssumeRoleRequest request = new AssumeRoleRequest();
            request.setVersion(sts_api_version);
            request.setMethod(MethodType.POST);
            request.setProtocol(ProtocolType.HTTPS);
            request.setRoleArn(roleArn);
            request.setRoleSessionName(roleSessionName);
            request.setPolicy(policy);
            // 发起请求，并得到response
            AssumeRoleResponse response = client.getAcsResponse(request);
            return response;
        } catch (ClientException e) {
            System.out.println("Failed to get a token.");
            System.out.println("Error code: " + e.getErrCode());
            System.out.println("Error message: " + e.getErrMsg());
            throw e;
        }
    }

    public AssumeRoleResponse.Credentials getStsCredentials(String userId, String userName) throws Exception {
        String roleSessionName = getRoleSessionName(userId, userName);
        String policy = "{\n" +
                "    \"Version\": \"1\", \n" +
                "    \"Statement\": [\n" +
                "        {\n" +
                "            \"Action\": [\n" +
                "                \"oss:GetBucket\", \n" +
                "                \"oss:GetObject\" \n" +
                "            ], \n" +
                "            \"Resource\": [\n" +
                "                \"acs:oss:*:*:*\"\n" +
                "            ], \n" +
                "            \"Effect\": \"Allow\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        AssumeRoleResponse response = assumeRole(roleSessionName, policy);
        return response.getCredentials();
    }

//    public static void main(String[] args) throws Exception {
//        AssumeRoleResponse.Credentials credentials = new StsService().getStsCredentials("fd", "fd");
//        System.out.println("Expiration: " + credentials.getExpiration());
//        System.out.println("Access Key Id: " + credentials.getAccessKeyId());
//        System.out.println("Access Key Secret: " + credentials.getAccessKeySecret());
//        System.out.println("Security Token: " + credentials.getSecurityToken());
//    }
}
