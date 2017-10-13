package com.fengdui.test.extjs.controller;

import com.fengdui.test.extjs.pojo.Role;
import com.fengdui.test.extjs.service.RoleService;
import com.fengdui.test.extjs.utils.ExtJSResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @author FD
 * @date 2017/3/15
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;
//    @RequestMapping("/list")
//    public ExtJSResponse list(String userName){
//        List<User> users = userBO.list(userName);
//        return ExtJSResponse.successRes4Find(users, users.size());
//    }
    @RequestMapping("/query")
    public ExtJSResponse list(String roleName) throws IOException {

        System.out.println(roleName);
//        CloseableHttpClient httpclient = HttpClients.createDefault();
//        HttpGet httpget = new HttpGet("http://ip.taobao.com/service/getIpInfo.php?"+"ip="+ipAddress);
//        System.out.println("executing request " + httpget.getURI());
//        CloseableHttpResponse response = httpclient.execute(httpget);
//        HttpEntity entity = response.getEntity();
//        System.out.println("Response content: " + EntityUtils.toString(entity));
        List<Role> roles = roleService.list(roleName);
        return ExtJSResponse.successRes4Find(roles, roles.size());
    }
    @RequestMapping("/add")
    public ExtJSResponse add(@RequestBody Role role)  {
        System.out.println(role.getRole() +" "+role.getDescription());
        roleService.add(role);
        return ExtJSResponse.success();
    }
}