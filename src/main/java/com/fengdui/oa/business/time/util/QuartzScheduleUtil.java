package com.fengdui.oa.business.time.util;

import com.xh.market.business.time.common.QuartzConstant;
import com.xh.market.business.time.entity.TimeTask;
import org.quartz.*;
import org.quartz.Trigger.TriggerState;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author fd
 * @date 2015年10月9日
 * @desc QuartzScheduleUtil.java
 */

public class QuartzScheduleUtil {
	private static Scheduler scheduler = getScheduler();

	/** 创建一个调度对象 */
	private static Scheduler getScheduler() {
		SchedulerFactory factory = new StdSchedulerFactory();
		Scheduler scheduler = null;
		try {
			scheduler = factory.getScheduler();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scheduler;
	}

	public static Scheduler getInstanceScheduler() {
		return scheduler;
	}

	/** 获得job的执行情况 */
	public static TimeTask getTaskInfo(TimeTask timeTask) throws SchedulerException {
		JobKey jobKey = new JobKey(QuartzConstant.PRE_JOB_NAME + timeTask.getId());
		JobDetail jobDetail = scheduler.getJobDetail(jobKey);
		TriggerKey triggerKey = new TriggerKey(QuartzConstant.PRE_TRIGGER_NAME + timeTask.getId());
		Trigger trigger = scheduler.getTrigger(triggerKey);
		if (null != jobDetail && null != trigger) {
			JobDataMap data = jobDetail.getJobDataMap();
			TriggerState state = scheduler.getTriggerState(triggerKey);//任务状态
			timeTask.setState(state.name());
			
			//Date startDate = trigger.getStartTime();
			//Date endDate = trigger.getEndTime();
			//Date finalDate = trigger.getFinalFireTime();
			Date nextDate = trigger.getNextFireTime();// 下次执行时间
			Date preDate = trigger.getPreviousFireTime();// 上次执行时间
			if(null != preDate){
				timeTask.setLastExecTime(preDate);
				if(data.containsKey(QuartzConstant.KEY_USED_TIME)){
					timeTask.setUsedTime(data.getLong(QuartzConstant.KEY_USED_TIME));
				}
			}
			System.out.println("++++++++++++");
			timeTask.setNextExecTime(nextDate);
			if (data.containsKey(QuartzConstant.KEY_EXECUTION_COUNT)) {
				timeTask.setExecCount(data.getInt(QuartzConstant.KEY_EXECUTION_COUNT));//执行次数
				System.out.println(QuartzConstant.KEY_EXECUTION_COUNT);
				System.out.println("*************");
			}
			if(data.containsKey(QuartzConstant.KEY_EXCEPTION_COUNT)){
				timeTask.setExceptionCount(data.getInt(QuartzConstant.KEY_EXCEPTION_COUNT));//异常数
			}
			if(data.containsKey(QuartzConstant.KEY_EXCEPTION_TIME)){
				timeTask.setLastExceptionTime((Date)data.get(QuartzConstant.KEY_EXCEPTION_TIME));// 上次异常时间
			}
			if(data.containsKey(QuartzConstant.KEY_EXCEPTION_DESC)){
				timeTask.setLastExceptionDesc(data.getString(QuartzConstant.KEY_EXCEPTION_DESC));// 上次异常描述
			}
		}
		return timeTask;
	}
	
	/** 判断表达式是否正确 */
	public static boolean validateCronExpression(String cronExpress){
		return CronExpression.isValidExpression(cronExpress);
	}
	
	/** 判断类名是否正确 */
	public static String validateClassName(String className){
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (Exception e) {
			return "该类不存在";
		}
		try {
			JobBuilder.newJob(clazz).build();
		} catch (Exception e) {
			return "该类没有继承Job接口";
		}
		return null;
	}

	/** 启动一个调度对象 */
	public void start() throws SchedulerException {
		scheduler.start();
	}

	/** 检查调度是否启动 */
	public boolean isStarted() throws SchedulerException {
		return scheduler.isStarted();
	}

	/** 关闭调度信息 */
	public void shutdown() throws SchedulerException {
		scheduler.shutdown();
	}

	/** 添加调度的job信息 */
	public Date scheduleJob(JobDetail jobDetail, Trigger trigger)
			throws SchedulerException {
		return scheduler.scheduleJob(jobDetail, trigger);
	}

	/** 添加相关的触发器 */
	public Date scheduleJob(Trigger trigger) throws SchedulerException {
		return scheduler.scheduleJob(trigger);
	}

	/** 添加多个job任务 */
	/*public void scheduleJobs(Map<JobDetail, List<Trigger>> triggersAndJobs,
			boolean replace) throws SchedulerException {
		scheduler.scheduleJobs(triggersAndJobs, replace);
	}*/
	public void scheduleJobs(Map<JobDetail, Set<? extends Trigger>> triggersAndJobs,
			boolean replace) throws SchedulerException {
		scheduler.scheduleJobs(triggersAndJobs, replace);
	}
	/** 停止调度Job任务 */
	public boolean unscheduleJob(TriggerKey triggerKey)
			throws SchedulerException {
		return scheduler.unscheduleJob(triggerKey);
	}

	/** 停止调度多个触发器相关的job */
	public boolean unscheduleJobs(List<TriggerKey> triggerKeyList)
			throws SchedulerException {
		return scheduler.unscheduleJobs(triggerKeyList);
	}

	/** 重新恢复触发器相关的job任务 */
	public Date rescheduleJob(TriggerKey triggerKey, Trigger trigger)
			throws SchedulerException {
		return scheduler.rescheduleJob(triggerKey, trigger);
	}

	/** 添加相关的job任务 */
	public void addJob(JobDetail jobDetail, boolean flag)
			throws SchedulerException {
		scheduler.addJob(jobDetail, flag);
	}

	/** 删除相关的job任务 */
	public boolean deleteJob(JobKey jobKey) throws SchedulerException {
		return scheduler.deleteJob(jobKey);
	}

	/** 删除相关的多个job任务 */
	public boolean deleteJobs(List<JobKey> jobKeys) throws SchedulerException {
		return scheduler.deleteJobs(jobKeys);
	}

	public void triggerJob(JobKey jobKey) throws SchedulerException {
		scheduler.triggerJob(jobKey);
	}

	public void triggerJob(JobKey jobKey, JobDataMap jobDataMap)
			throws SchedulerException {
		scheduler.triggerJob(jobKey, jobDataMap);
	}

	/** 停止一个job任务 */
	public void pauseJob(JobKey jobKey) throws SchedulerException {
		scheduler.pauseJob(jobKey);
	}

	/** 停止多个job任务 */
	public void pauseJobs(GroupMatcher<JobKey> groupMatcher)
			throws SchedulerException {
		scheduler.pauseJobs(groupMatcher);
	}

	/** 停止使用相关的触发器 */
	public void pauseTrigger(TriggerKey triggerKey) throws SchedulerException {
		scheduler.pauseTrigger(triggerKey);
	}

	/** 停止多个触发器 */
	public void pauseTriggers(GroupMatcher<TriggerKey> groupMatcher)
			throws SchedulerException {
		scheduler.pauseTriggers(groupMatcher);
	}

	/** 恢复相关的job任务 */
	public void resumeJob(JobKey jobKey) throws SchedulerException {
		scheduler.resumeJob(jobKey);
	}

	/** 恢复多个job任务 */
	public void resumeJobs(GroupMatcher<JobKey> groupMatcher)
			throws SchedulerException {
		scheduler.resumeJobs(groupMatcher);
	}

	/** 恢复相关的Trigger */
	public void resumeTrigger(TriggerKey triggerKey) throws SchedulerException {
		scheduler.resumeTrigger(triggerKey);
	}

	/** 恢复多个Trigger */
	public void resumeTriggers(GroupMatcher<TriggerKey> groupMatcher)
			throws SchedulerException {
		scheduler.resumeTriggers(groupMatcher);
	}

	/** 暂停调度中所有的job任务 */
	public void pauseAll() throws SchedulerException {
		scheduler.pauseAll();
	}

	/** 恢复调度中所有的job的任务 */
	public void resumeAll() throws SchedulerException {
		scheduler.resumeAll();
	}
}