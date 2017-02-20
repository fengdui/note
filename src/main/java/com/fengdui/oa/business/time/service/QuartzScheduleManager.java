package com.fengdui.oa.business.time.service;

import com.xh.market.business.time.common.QuartzConstant;
import com.xh.market.business.time.entity.TimeTask;
import com.xh.market.business.time.job.TestJob;
import com.xh.market.business.time.util.QuartzScheduleUtil;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author fd
 * @date 2015年10月12日
 * @desc QuartzScheduleManager.java
 */

@Service
public class QuartzScheduleManager {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private Scheduler scheduler = QuartzScheduleUtil.getInstanceScheduler();

	public void run() throws SchedulerException {
		try {
			// Date runTime = evenMinuteDate(new Date());
			JobDetail job = newJob(TestJob.class).withIdentity("job_0")
					.requestRecovery().build();
			Trigger trigger = newTrigger().withIdentity("trigger_0")
					.withSchedule(cronSchedule("0/10 * * * * ?")).build();

			// Trigger trigger = newTrigger().withIdentity("trigger_0")
			// .withSchedule(cronSchedule("0 36 18 * * ?")).build();

			if (!scheduler.checkExists(job.getKey())) {
				scheduler.scheduleJob(job, trigger);
			}
			startScheduler();
		} catch (Exception e) {
			logger.error("run task error", e);
		}
	}

	public void run(List<TimeTask> taskList) {
		try {
			for (TimeTask task : taskList) {
				try {
					addJob(task);
				} catch (Exception e) {
					logger.error("add job error", e);
				}
			}
			startScheduler();
		} catch (Exception e) {
			logger.error("run task error", e);
		}
	}

	private void addJob(TimeTask task) throws SchedulerException {
		Class clazz = null;
		try {
			clazz = Class.forName(task.getClassName());
		} catch (Exception e) {
			logger.error("class not found", e);
		}
		if (null != clazz) {
			JobDetail jobDetail = newJob(clazz)
					.withIdentity(QuartzConstant.PRE_JOB_NAME + task.getId())
					.requestRecovery().build();
			Trigger trigger = newTrigger()
					.withIdentity(
							QuartzConstant.PRE_TRIGGER_NAME + task.getId())
					.withSchedule(cronSchedule(task.getCronExpress())).build();
			if (!scheduler.checkExists(jobDetail.getKey())) {
				scheduler.scheduleJob(jobDetail, trigger);
			}
		}
	}

	public void changeJob(TimeTask task, Integer actionType) throws Exception {
		Class clazz = null;
		try {
			clazz = Class.forName(task.getClassName());
		} catch (Exception e) {
			logger.error("class not found", e);
			throw new Exception("类不存在");
		}
		if (!CronExpression.isValidExpression(task.getCronExpress())) {
			throw new Exception("表达式有误");
		}
		if (null != clazz) {
			JobDetail jobDetail = newJob(clazz)
					.withIdentity(QuartzConstant.PRE_JOB_NAME + task.getId())
					.requestRecovery().build();
			Trigger trigger = newTrigger()
					.withIdentity(
							QuartzConstant.PRE_TRIGGER_NAME + task.getId())
					.withSchedule(cronSchedule(task.getCronExpress())).build();
			if (null != actionType) {
				switch (actionType) {
				case QuartzConstant.ACTION_START:
					if (!scheduler.checkExists(jobDetail.getKey())) {
						scheduler.scheduleJob(jobDetail, trigger);
					}
					break;
				case QuartzConstant.ACTION_STOP:
					if (scheduler.checkExists(jobDetail.getKey())) {
						scheduler.unscheduleJob(trigger.getKey());
					}
					break;
				case QuartzConstant.ACTION_PAUSE:
					if (scheduler.checkExists(jobDetail.getKey())) {
						scheduler.pauseJob(jobDetail.getKey());
					}
					break;
				case QuartzConstant.ACTION_RESUME:
					if (scheduler.checkExists(jobDetail.getKey())) {
						scheduler.resumeJob(jobDetail.getKey());
					}
					break;
				case QuartzConstant.ACTION_RESTART:
					if (scheduler.checkExists(jobDetail.getKey())) {
						scheduler.unscheduleJob(trigger.getKey());
					}
					scheduler.scheduleJob(jobDetail, trigger);
					break;
				case QuartzConstant.ACTION_KEEP:
					TriggerKey triggerKey = new TriggerKey(QuartzConstant.PRE_TRIGGER_NAME + task.getId());
					Trigger triggerOld = scheduler.getTrigger(triggerKey);
					if(null != triggerOld){
						String state = scheduler.getTriggerState(triggerKey).name();
						scheduler.unscheduleJob(triggerKey);
						scheduler.scheduleJob(jobDetail, trigger);
						if("PAUSED".equals(state)){
							scheduler.pauseJob(jobDetail.getKey());
						}
					}
					break;
				default:
					break;
				}
			}
			startScheduler();
		}
	}

	/** 启动Job *//*
	public void startJob(Integer id) throws SchedulerException {
		TimeTask task = timeTaskService.get(id);
		addJob(task);
		startScheduler();
	}*/

	/** 启动Job */
	public void startJob(TimeTask task) throws SchedulerException {
		addJob(task);
		startScheduler();
	}

	/** 启动调度 */
	private void startScheduler() throws SchedulerException {
		if (!scheduler.isStarted()) {
			scheduler.start();
		}
		SchedulerMetaData metaData = scheduler.getMetaData();
		logger.info("executed " + metaData.getNumberOfJobsExecuted() + " jobs");
	}
}