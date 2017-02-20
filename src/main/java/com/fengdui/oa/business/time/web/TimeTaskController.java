package com.fengdui.oa.business.time.web;

import com.xh.market.business.time.common.QuartzConstant;
import com.xh.market.business.time.entity.TimeTask;
import com.xh.market.business.time.service.QuartzScheduleManager;
import com.xh.market.business.time.service.TimeTaskService;
import com.xh.market.business.time.util.QuartzScheduleUtil;
import com.xh.market.framework.common.IdNameObj;
import com.xh.market.framework.constant.Cue;
import com.xh.market.framework.orm.MybatisService;
import com.xh.market.framework.web.BaseController;
import com.xh.market.framework.web.PageRespData;
import com.xh.market.framework.web.TableHeadInfo;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fd
 * @date 2015年10月9日
 * @desc TimeTaskController.java
 */
@Controller
@RequestMapping("/time/timetask")
public class TimeTaskController extends BaseController<TimeTask> {

	private static final Logger logger = LoggerFactory.getLogger(TimeTaskController.class);

	@Autowired
	private TimeTaskService timeTaskService;

	@Autowired
	private QuartzScheduleManager quartzScheduleManager;
	 
	@Override
	protected Logger getLogger() {
		return logger;
	}

	@Override
	protected String getUrlMain() {
		return "/time/timetask";
	}

	@Override
	protected MybatisService getService() {
		return timeTaskService;
	}

	@Override
	protected TimeTask prepareEntity() {
		return new TimeTask();
	}
	@Override
	public void listExtra(List<TimeTask> list) {
		for(TimeTask t : list) {
			try {
				t = QuartzScheduleUtil.getTaskInfo(t);
			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				logger.error("getTaskInfo error" + e);
			}
		}
	}
	@Override
	protected List<TableHeadInfo> buildTableHeadList() {
		List<TableHeadInfo> headList = new ArrayList<TableHeadInfo>();
		headList.add(new TableHeadInfo("center", "", TableHeadInfo.TYPE_CHECKBOX));
		headList.add(new TableHeadInfo("center col-md-1 newCss", "序号"));
		headList.add(new TableHeadInfo("center col-md-1", "任务标题"));
		headList.add(new TableHeadInfo("center col-md-1", "表达式"));
		headList.add(new TableHeadInfo("center col-md-1", "状态"));
		List<IdNameObj> selfStartStr = new ArrayList<>();
		selfStartStr.add(new IdNameObj("", "全部"));
		selfStartStr.add(new IdNameObj(Boolean.TRUE, "TRUE"));
		selfStartStr.add(new IdNameObj(Boolean.FALSE, "FALSE"));
		headList.add(new TableHeadInfo("center col-md-1 roleSelTH", "自启动", TableHeadInfo.TYPE_SEL, "filter#EQ#self_start", selfStartStr));
		headList.add(new TableHeadInfo("center col-md-1", "类名"));
		headList.add(new TableHeadInfo("center col-md-1", "本次执行消耗时间 /s"));
		headList.add(new TableHeadInfo("center col-md-1", "上次执行时间"));
		headList.add(new TableHeadInfo("center col-md-1", "下次执行时间"));
		headList.add(new TableHeadInfo("center col-md-1 newCss",  "执行次数"));
		headList.add(new TableHeadInfo("center col-md-1 newCss", "异常数"));
		headList.add(new TableHeadInfo("center col-md-1", "上次异常时间"));
		headList.add(new TableHeadInfo("center col-md-1", "上次异常描述"));
		headList.add(new TableHeadInfo("center col-md-1", "任务描述"));
		headList.add(new TableHeadInfo("center col-md-1", "操作"));
		return headList;
	}

	@RequestMapping(value = "/input", method = RequestMethod.POST)
	@ResponseBody
	public Object save(TimeTask t, boolean stateChanged) {
		System.out.println(stateChanged);
		try {
			saveEntity(t);
			if(stateChanged) {
				quartzScheduleManager.changeJob(t, QuartzConstant.ACTION_KEEP);
			}
		} catch (Exception e) {
			getLogger().error("save error : " + e);
			return new PageRespData(false, Cue.SAVE_FAIL, e.getMessage());
		}
		return new PageRespData(true, Cue.SAVE_SUCCESS);
	}
	@RequestMapping(value = "/changeJob", method = RequestMethod.POST)
	@ResponseBody
	public Object changeJob(Integer id, Integer actionType) {
		try {
			quartzScheduleManager.changeJob(timeTaskService.get(id), actionType);
		}
		catch (Exception e) {
			getLogger().error("changeJob error：" + e);
			return new PageRespData(false, Cue.FAIL, e.getMessage());
		}
		return new PageRespData(true, Cue.SUCCESS);
	}
	@RequestMapping(value = "/vaildCronExpress", method = RequestMethod.GET)
	@ResponseBody
	public Object VaildCronExpress(String cronExpress) {
		boolean vaildExpress = timeTaskService.validateCronExpression(cronExpress);
		if(vaildExpress == false) {
			return new PageRespData(false, "表达式错误");
		}
		else {
			return new PageRespData(true, "表达式正确");
		}
	}
	@RequestMapping(value = "/vaildClazzName", method = RequestMethod.GET)
	@ResponseBody
	public Object VaildJobName(String clazzName) {
		String vaildClazzName = timeTaskService.validateClassName(clazzName);
		if(vaildClazzName == null)
			return new PageRespData(true, "任务名正确");
		else
			return new PageRespData(false, "任务名错误");
	}
}
