package com.mycat.test.service;

import com.base.exception.ServiceException;
import com.base.service.IBasicService;
import com.mycat.test.model.User;

import java.util.List;

/**
 *
 */
public interface IUserService extends IBasicService<User>{

    /**
    * 获取用户所属信息
    * @param userId
    * @return
    * @throws com.base.exception.ServiceException
    */
    public List<User> getListByUserId(Long userId) throws ServiceException;

}
