package com.fengdui.extjs.service.impl;

import com.fengdui.extjs.mapper.RoleMapper;
import com.fengdui.extjs.pojo.Role;
import com.fengdui.extjs.pojo.RoleExample;
import com.fengdui.extjs.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author FD
 * @date 2017/3/15
 */

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> list(String roleName) {
        RoleExample example = new RoleExample();
        if (!StringUtils.isEmpty(roleName)) {
            example.createCriteria().andRoleLike("%"+roleName+"%");
        }
        return roleMapper.selectByExample(example);
    }

    @Override
    public void add(Role role) {
        roleMapper.insert(role);
    }
}
