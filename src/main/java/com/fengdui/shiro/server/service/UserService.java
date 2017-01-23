package com.fengdui.shiro.server.service;

import com.zheyue.authserver.datasource.DataSource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by fd on 2016/10/18.
 */

public interface UserService {

    @DataSource(name = "BasicDataSource")
    @Transactional(propagation = Propagation.REQUIRED)
    void save();

    @DataSource(name = "BasicDataSource2")
    @Transactional(propagation = Propagation.MANDATORY)
    void save2();
}
