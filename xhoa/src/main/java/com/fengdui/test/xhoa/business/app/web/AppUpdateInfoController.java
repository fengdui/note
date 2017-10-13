package com.fengdui.test.xhoa.business.app.web;
import com.fengdui.test.xhoa.business.app.entity.AppUpdateInfo;
import com.fengdui.test.xhoa.business.app.entity.AppVersionInfo;
import com.fengdui.test.xhoa.business.app.service.AppUpdateInfoService;
import com.fengdui.test.xhoa.business.app.service.AppVersionInfoService;
import com.fengdui.test.xhoa.framework.common.IdNameObj;
import com.fengdui.test.xhoa.framework.constant.Cue;
import com.fengdui.test.xhoa.framework.orm.MybatisService;
import com.fengdui.test.xhoa.framework.web.BaseController;
import com.fengdui.test.xhoa.framework.web.PageRespData;
import com.fengdui.test.xhoa.framework.web.TableHeadInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author fd
 * @date 2015年10月19日
 * @desc AppUpdateInfoController.java
 */
@Controller
@RequestMapping("/app/appupdate")
public class AppUpdateInfoController extends BaseController<AppUpdateInfo> {
	private static final Logger logger = LoggerFactory.getLogger(AppUpdateInfo.class);
	@Autowired
	private AppUpdateInfoService appUpdateInfoService;
	@Autowired
	private AppVersionInfoService appVersionInfoService;
	@Override
	protected Logger getLogger() {
		return logger;
	}

	@Override
	protected String getUrlMain() {
		return "/app/appupdate";
	}

	@Override
	protected MybatisService getService() {
		return appUpdateInfoService;
	}

	@Override
	protected AppUpdateInfo prepareEntity() {
		return new AppUpdateInfo();
	}
	@Override
	protected List<TableHeadInfo> buildTableHeadList() {
		List<TableHeadInfo> headList = new ArrayList<TableHeadInfo>();
		headList.add(new TableHeadInfo("center col-md-1", "", TableHeadInfo.TYPE_CHECKBOX));
		headList.add(new TableHeadInfo("center col-md-1", "序号"));
		List<IdNameObj>  strategyList = new ArrayList<IdNameObj>();
		strategyList.add(new IdNameObj(null, "全部策略"));
		strategyList.add(new IdNameObj(Integer.valueOf(1), "强制更新"));
		strategyList.add(new IdNameObj(Integer.valueOf(2), "建议更新"));
		headList.add(new TableHeadInfo("center col-md-1 roleSelTH", null, TableHeadInfo.TYPE_SEL, "filter#EQ#update_strategy", strategyList));
		headList.add(new TableHeadInfo("center col-md-1", "更新最低版本"));
		headList.add(new TableHeadInfo("center col-md-1", "更新最高版本"));
		headList.add(new TableHeadInfo("center col-md-1", "更新至版本"));
		headList.add(new TableHeadInfo("center col-md-1", "更新说明"));
		headList.add(new TableHeadInfo("center col-md-1", "更新文件"));
		headList.add(new TableHeadInfo("center col-md-1", "操作"));
		return headList;
	}
	@RequestMapping(value="/vaildVersion", method = RequestMethod.GET)
	@ResponseBody
	public Object vaildVersion_st(Integer versionNum, Integer versionNum2) {
	
		if(appUpdateInfoService.insertCheck(versionNum, versionNum2)) {
			return new PageRespData(true, Cue.SUCCESS);
		}
		return new PageRespData(false, Cue.FAIL);
	}
	@Override
	protected void editExtra(Map<String, Object> valMap) {
		List<AppVersionInfo> versionList = appVersionInfoService.queryAll();
		System.out.println(versionList.size());
		valMap.put("versionList", versionList);
	}
}
