package com.fengdui.extjs.controller;

import com.fengdui.extjs.pojo.Ip;
import com.fengdui.extjs.utils.ExtJSResponse;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * @author FD
 * @date 2017/3/15
 */
@RestController
@RequestMapping("/ip")
public class IpController {

//    @RequestMapping("/list")
//    public ExtJSResponse list(String userName){
//        List<User> users = userBO.list(userName);
//        return ExtJSResponse.successRes4Find(users, users.size());
//    }
    @RequestMapping("/query")
    public Object list(String ipAddress) throws IOException {
        System.out.println(ipAddress);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("http://ip.taobao.com/service/getIpInfo.php?"+"ip="+ipAddress);
        System.out.println("executing request " + httpget.getURI());
        CloseableHttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        System.out.println("Response content: " + EntityUtils.toString(entity));
        Ip ip = new Ip();
        ip.setIp("123456");
        ip.setAddress("杭州");
        return ExtJSResponse.successRes4Find(ip, 1);
    }
}