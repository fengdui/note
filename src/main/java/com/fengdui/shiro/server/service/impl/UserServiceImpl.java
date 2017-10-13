package com.fengdui.shiro.server.service.impl;

import com.zheyue.authserver.dao.UserDao;
import com.zheyue.authserver.dao.UserMapper;
import com.zheyue.authserver.datasource.DataSource;
import com.zheyue.authserver.service.UserService;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by fd on 2016/10/18.
 */
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public void save() {
//        System.out.println("开始执行sava()方法");
//        try {
//            UserService userService = (UserService) AopContext.currentProxy();
//            userService.save2();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        finally {
//
//        }
        userMapper.select();
    }

    public void save2() {
        System.out.println("开始执行sava2()方法");
    }
}
