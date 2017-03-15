package com.fengdui.extjs.controller;

import com.fengdui.extjs.pojo.Role;
import com.fengdui.extjs.utils.ExtJSResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author FD
 * @date 2017/3/15
 */
@RestController
@RequestMapping("/role")
public class RoleController {

//    @RequestMapping("/list")
//    public ExtJSResponse list(String userName){
//        List<User> users = userBO.list(userName);
//        return ExtJSResponse.successRes4Find(users, users.size());
//    }
    @RequestMapping("/query")
    public Object list(String roleName) throws IOException {

        System.out.println(roleName);
//        CloseableHttpClient httpclient = HttpClients.createDefault();
//        HttpGet httpget = new HttpGet("http://ip.taobao.com/service/getIpInfo.php?"+"ip="+ipAddress);
//        System.out.println("executing request " + httpget.getURI());
//        CloseableHttpResponse response = httpclient.execute(httpget);
//        HttpEntity entity = response.getEntity();
//        System.out.println("Response content: " + EntityUtils.toString(entity));
        Role role = new Role();
        role.setRole("java");
        role.setDescription("java研发");
        return ExtJSResponse.successRes4Find(role, 1);
    }
}