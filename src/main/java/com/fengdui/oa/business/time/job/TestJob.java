package com.fengdui.oa.business.time.job;

import com.xh.market.business.time.common.QuartzConstant;
import org.quartz.*;

import java.util.Date;

/**
 * @author fd
 * @date 2015年9月30日
 * @desc TestJob.java
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class TestJob implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Date dateBegin = new Date();
		JobDataMap data = context.getJobDetail().getJobDataMap();
		int exeCount = 0;//执行次数
		int exceptionCount = 0;//异常次数
		try {
			if (data.containsKey(QuartzConstant.KEY_EXECUTION_COUNT)) {
				exeCount = data.getInt(QuartzConstant.KEY_EXECUTION_COUNT);
			}
			if (data.containsKey(QuartzConstant.KEY_EXCEPTION_COUNT)) {
				exceptionCount = data.getInt(QuartzConstant.KEY_EXCEPTION_COUNT);
			}
		} catch (Exception e) {
			data.put(QuartzConstant.KEY_EXCEPTION_TIME, new Date());
			data.put(QuartzConstant.KEY_EXCEPTION_DESC, e.toString());
			exceptionCount++;
			data.put(QuartzConstant.KEY_EXCEPTION_COUNT, exceptionCount);
		}
		finally {
			exeCount++;
			data.put(QuartzConstant.KEY_EXECUTION_COUNT, exeCount);
			Date dateEnd = new Date();
			long usedTime = dateEnd.getTime() - dateBegin.getTime();
			data.put(QuartzConstant.KEY_USED_TIME, usedTime);
		}
	}
}
