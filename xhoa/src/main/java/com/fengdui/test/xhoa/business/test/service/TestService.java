package com.fengdui.test.xhoa.business.test.service;

import com.fengdui.test.xhoa.business.test.dao.TestDao;
import com.fengdui.test.xhoa.framework.orm.MultiDataSourceAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Wander.Zeng
 * @data 2015-6-2 下午3:40:50
 * @desc TestService.java
 */
@Service
@Transactional
@MultiDataSourceAnnotation(key = "dataSource")
public class TestService {

	private static final Logger logger = LoggerFactory.getLogger(TestService.class);

	@Autowired
	private TestDao testDao;

	// @Transactional(readOnly = true)
	public void test() throws Exception {
		testDao.test();
		testDao.test2();
		logger.error("test");
		// throw new RuntimeException("test trans");
	}

}
