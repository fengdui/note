package com.fengdui.oa.business.test.service;

import com.xh.market.business.test.dao.TestTmpDao;
import com.xh.market.framework.orm.MultiDataSourceAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Wander.Zeng
 * @data 2015-6-17 上午10:12:43
 * @desc TestTmpService.java
 */
@Service
@Transactional
@MultiDataSourceAnnotation(key = "dataSourceTemp")
public class TestTmpService {

	private static final Logger logger = LoggerFactory.getLogger(TestTmpService.class);

	@Autowired
	private TestTmpDao testTmpDao;

	public void test() throws Exception {
		testTmpDao.test();
		logger.error("test");
	}

}
