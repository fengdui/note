package com.mycat.test.dao;

import com.mycat.test.model.Test;
import org.springframework.stereotype.Repository;
import com.base.dao.MybatisBasicDao;

/**
* @ClassName:TestDao
* @Description: test
* @author: suxl
* @date:2015-01-19 16:40:14
*/
@Repository("testDao")
public class TestDao extends MybatisBasicDao<Test> implements ITestDao {

	public TestDao(){
		super(Test.class);
	}
}
