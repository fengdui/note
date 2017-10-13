package com.mycat.test.service;

import com.base.dao.IBasicDao;
import com.base.exception.DaoException;
import com.base.exception.ServiceException;
import com.base.service.BasicService;
import com.mycat.test.dao.IRoleDao;
import com.mycat.test.dao.IUserDao;
import com.mycat.test.model.Role;
import com.mycat.test.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author FD
 * @date 2017/9/5
 */
@Service
public class RoleService extends BasicService<Role> implements IRoleService {
    @Autowired
    @Qualifier("roleDao")
    private IRoleDao dao;

    @Override
    public IBasicDao<Role> getDao() {
        return this.dao;
    }


    public List<Role> getListByRoleId(Long roleId) throws ServiceException {
        try{
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("role_id", roleId);
            return this.dao.find(map);
        }catch (DaoException e){
            throw new ServiceException(e);
        }
    }
}