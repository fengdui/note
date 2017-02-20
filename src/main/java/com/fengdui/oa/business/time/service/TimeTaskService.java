package com.fengdui.oa.business.time.service;

import com.xh.market.business.time.common.QuartzConstant;
import com.xh.market.business.time.dao.TimeTaskDao;
import com.xh.market.business.time.entity.TimeTask;
import com.xh.market.business.time.util.QuartzScheduleUtil;
import com.xh.market.framework.orm.MybatisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fd
 * @date 2015年10月9日
 * @desc TimeTaskService.java
 */
@Service
public class TimeTaskService extends MybatisService<TimeTask, Integer, TimeTaskDao>{

	@Autowired
	private TimeTaskDao timeTaskDao;
	@Autowired
	private QuartzScheduleManager quartzScheduleManager;
	@Override
	protected TimeTaskDao getDao() {
		return timeTaskDao;
	}
	public void deleteTask(List<Integer> ids) throws Exception{
		for (Integer id : ids) {
			TimeTask task = get(id);
			quartzScheduleManager.changeJob(task, QuartzConstant.ACTION_STOP);
			delete(task);
		}
	}
	
	public boolean validateCronExpression(String cronExpress){
		return QuartzScheduleUtil.validateCronExpression(cronExpress);
	}
	
	public String validateClassName(String className){
		return QuartzScheduleUtil.validateClassName(className);
	}
}
