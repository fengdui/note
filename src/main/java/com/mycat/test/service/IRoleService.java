package com.mycat.test.service;

import com.base.exception.ServiceException;
import com.base.service.IBasicService;
import com.mycat.test.model.Role;

import java.util.List;

/**
 * @author FD
 * @date 2017/9/5
 */
public interface IRoleService extends IBasicService<Role> {

    public List<Role> getListByRoleId(Long roleId) throws ServiceException;
}
