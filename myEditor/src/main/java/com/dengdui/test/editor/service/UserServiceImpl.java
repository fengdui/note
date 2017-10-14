package com.dengdui.test.editor.service;

import com.dengdui.test.editor.common.Result;
import com.dengdui.test.editor.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.ResultSet;
import java.sql.SQLException;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("login")
    public Result login(String id, String pwd) {
        String sql = "select * from user where name = ? and passwd = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new Object[]{id, pwd}, new RowMapper<User>() {
                public User mapRow(ResultSet rs, int i) throws SQLException {
                    User user = new User();
                    return user;
                }
            });
            return new Result(true, "登录成功", user);
        }catch(Exception e) {
            return new Result(false, "登录失败");
        }
    }
}
