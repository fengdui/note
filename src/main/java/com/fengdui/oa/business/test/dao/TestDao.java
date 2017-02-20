package com.fengdui.oa.business.test.dao;

import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

/**
 * @author Wander.Zeng
 * @data 2015-6-2 下午3:33:20
 * @desc TestDao.java
 */
@Repository
public interface TestDao {

	public void test();

	@Insert("INSERT INTO tbl_test (test) VALUES('www')")
	public void test2();

}
