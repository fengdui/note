package com.fengdui.oa.business.time.dao;

import com.xh.market.business.time.entity.TimeTask;
import com.xh.market.framework.orm.MybatisDao;
import org.springframework.stereotype.Repository;

/**
 * @author fd
 * @date 2015年10月9日
 * @desc TimeTaskDao.java
 */
@Repository
public interface TimeTaskDao extends MybatisDao<TimeTask, Integer>{
	
}
