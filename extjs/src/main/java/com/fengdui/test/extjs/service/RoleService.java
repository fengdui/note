package com.fengdui.test.extjs.service;

import com.fengdui.test.extjs.pojo.Role;

import java.util.List;

/**
 * @author FD
 * @date 2017/3/15
 */
public interface RoleService {

    List<Role> list(String roleName);

    void add(Role role);
}
