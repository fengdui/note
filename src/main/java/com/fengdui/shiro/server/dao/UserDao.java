package com.fengdui.shiro.server.dao;

import com.zheyue.authserver.controller.User;
import org.springframework.stereotype.Repository;

/**
 * Created by fd on 2016/10/20.
 */
@Repository
public interface UserDao {
    User select();
}
