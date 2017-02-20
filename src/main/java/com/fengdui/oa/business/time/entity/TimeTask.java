package com.fengdui.oa.business.time.entity;

import com.xh.market.framework.orm.EntityExtend;
import com.xh.market.framework.orm.TagLogicDelete;
import com.xh.market.framework.orm.TagUniqueColumn;
import com.xh.market.framework.util.date.DateUtil;

import java.util.Date;

/**
 * @author fd
 * @date 2015年10月16日
 * @desc TimeTask.java
 */
@TagLogicDelete
public class TimeTask extends EntityExtend{
	
	private static final long serialVersionUID = -4095873240725877833L;
	private String name;// 任务标题
	@TagUniqueColumn(colName = "class_Name", colDesc = "类名")
	private String className;// 类名
	private String method;// 对应执行的方法
	private String cronExpress;// 表达式
	private String jobName;// Job名
	private String groupName;// Group名
	private boolean selfStart;// 自启动
	private String comment;// 备注
	private int execCount;// 执行次数
	private Date lastExecTime;// 上次执行时间
	private Date nextExecTime;// 下次执行时间
	private String state;// 任务状态
	private long usedTime;// 执行消耗时间ms
	private int exceptionCount;// 任务异常总数
	private Date lastExceptionTime;// 上次异常时间
	private String lastExceptionDesc;// 上次异常描述
	
	private String clazzName;// 类名
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
		this.clazzName = className;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getCronExpress() {
		return cronExpress;
	}
	public void setCronExpress(String cronExpress) {
		this.cronExpress = cronExpress;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public boolean getSelfStart() {
		return selfStart;
	}
	public void setSelfStart(boolean selfStart) {
		this.selfStart = selfStart;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getExecCount() {
		return execCount;
	}
	public void setExecCount(int execCount) {
		this.execCount = execCount;
	}
	public Date getLastExecTime() {
		return lastExecTime;
	}
	public String getLastExecTimeStr() {
		if(getLastExecTime() == null)
			return "未启动";
		return DateUtil.convertDate2String(getLastExecTime());
	}
	public void setLastExecTime(Date lastExecTime) {
		this.lastExecTime = lastExecTime;
	}
	public Date getNextExecTime() {
		return nextExecTime;
	}
	public String getNextExecTimeStr() {
		if(getNextExecTime() == null)
			return "未启动";
		return DateUtil.convertDate2String(getNextExecTime());
	}
	public void setNextExecTime(Date nextExecTime) {
		this.nextExecTime = nextExecTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public long getUsedTime() {
		return usedTime;
	}
	public double getUsedTimeToDouble() {
		System.out.println((double)usedTime/1000);
		return (double)usedTime/1000;
	}
	public void setUsedTime(long usedTime) {
		this.usedTime = usedTime;
	}
	public int getExceptionCount() {
		return exceptionCount;
	}
	public void setExceptionCount(int exceptionCount) {
		this.exceptionCount = exceptionCount;
	}
	public Date getLastExceptionTime() {
		return lastExceptionTime;
	}
	public String getLastExceptionTimeStr() {
		if(lastExceptionTime == null)
			return "无异常";
		return DateUtil.convertDate2String(getLastExceptionTime());
	}
	public void setLastExceptionTime(Date lastExceptionTime) {
		this.lastExceptionTime = lastExceptionTime;
	}
	
	public String getLastExceptionDesc() {
		if(lastExceptionDesc == null)
			return "无异常";
		return lastExceptionDesc;
	}
	public void setLastExceptionDesc(String lastExceptionDesc) {
		this.lastExceptionDesc = lastExceptionDesc;
	}
	public String getClazzName() {
		return clazzName;
	}
	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
		this.className = clazzName;
	}
	public String getShortClazzName() {
		return getClazzName().substring(getClazzName().lastIndexOf(".")+1);
	}
}
