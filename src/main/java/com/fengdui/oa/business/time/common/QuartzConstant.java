package com.fengdui.oa.business.time.common;

/**
 * @author fd
 * @date 2015年10月12日
 * @desc QuartzConstant.java
 */
public class QuartzConstant {
	/** 默认厂商名称 */
	public static final String MANUFACTURER_NAME_DEFAULT = "杭州旭航网络科技有限公司";
	
	/** 执行次数key */
	public static final String KEY_EXECUTION_COUNT = "exec_count";
	/** 异常次数key */
	public static final String KEY_EXCEPTION_COUNT = "exception_count";
	/** 执行消耗时间key */
	public static final String KEY_USED_TIME = "used_time";
	/** 异常时间key */
	public static final String KEY_EXCEPTION_TIME = "exeception_time";
	/** 异常描述key */
	public static final String KEY_EXCEPTION_DESC = "exeception_desc";
	
	/** Job Name前缀 */
	public static final String PRE_JOB_NAME = "job_";
	/** Trigger Name前缀 */
	public static final String PRE_TRIGGER_NAME = "trigger_";
	
	/** 启动 */
	public static final int ACTION_START = 0;
	/** 暂停 */
	public static final int ACTION_PAUSE = 1;
	/** 停止 */
	public static final int ACTION_STOP = 2;
	/** 恢复 */
	public static final int ACTION_RESUME = 3;
	/** 重启 */
	public static final int ACTION_RESTART = 4;
	/** 保持原状 */
	public static final int ACTION_KEEP = 5;
}