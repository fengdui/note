package com.mycat.test.service;

import com.base.exception.ServiceException;
import com.base.service.IBasicService;
import com.mycat.test.model.Test;

import java.util.List;

/**
* @ClassName:ITestService
* @Description: test
* @author: suxl
* @date:2015-01-19 16:40:14
*/
public interface ITestService extends IBasicService<Test>{

    /**
    * 获取用户所属信息
    * @param userId
    * @return
    * @throws com.base.exception.ServiceException
    */
    public List<Test> getListByUserId(Long userId) throws ServiceException;

}
