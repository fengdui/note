package com.mycat.test.service;

import com.base.dao.IBasicDao;
import com.base.exception.DaoException;
import com.base.exception.ServiceException;
import com.base.service.BasicService;
import com.mycat.test.dao.IUserDao;
import com.mycat.test.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @ClassName: UserService
* @Description: test
* @author: suxl
* @date:2015-01-19 16:40:14
*/

@Service
public class UserService extends BasicService<User> implements IUserService {
    @Autowired
    @Qualifier("userDao")
    private IUserDao dao;

    @Override
    public IBasicDao<User> getDao() {
        return this.dao;
    }

    public List<User> getListByUserId(Long userId) throws ServiceException {
        try{
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("user_id",userId);
            return this.dao.find(map);
        }catch (DaoException e){
            throw new ServiceException(e);
        }
    }
}
