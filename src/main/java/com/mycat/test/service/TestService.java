package com.mycat.test.service;

import com.base.dao.IBasicDao;
import com.base.exception.DaoException;
import com.base.exception.ServiceException;
import com.base.service.BasicService;
import com.mycat.test.dao.ITestDao;
import com.mycat.test.model.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @ClassName: TestService
* @Description: test
* @author: suxl
* @date:2015-01-19 16:40:14
*/

@Service
public class TestService extends BasicService<Test> implements ITestService {
    @Autowired
    @Qualifier("testDao")
    private ITestDao dao;

    @Override
    public IBasicDao<Test> getDao() {
        return this.dao;
    }

    public List<Test> getListByUserId(Long userId) throws ServiceException {
        try{
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("user_id",userId);
            return this.dao.find(map);
        }catch (DaoException e){
            throw new ServiceException(e);
        }
    }
}
