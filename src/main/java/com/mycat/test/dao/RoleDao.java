package com.mycat.test.dao;

import com.base.dao.MybatisBasicDao;
import com.mycat.test.model.Role;
import org.springframework.stereotype.Repository;

/**
 * @author FD
 * @date 2017/9/5
 */
@Repository("roleDao")
public class RoleDao extends MybatisBasicDao<Role> implements IRoleDao {

    public RoleDao(){
        super(Role.class);
    }
}