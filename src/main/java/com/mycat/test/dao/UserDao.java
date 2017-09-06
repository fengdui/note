package com.mycat.test.dao;

import com.mycat.test.model.User;
import org.springframework.stereotype.Repository;
import com.base.dao.MybatisBasicDao;

/**
* @ClassName:TestDao
* @Description: test
* @author: suxl
* @date:2015-01-19 16:40:14
*/
@Repository("userDao")
public class UserDao extends MybatisBasicDao<User> implements IUserDao {

	public UserDao(){
		super(User.class);
	}
}
