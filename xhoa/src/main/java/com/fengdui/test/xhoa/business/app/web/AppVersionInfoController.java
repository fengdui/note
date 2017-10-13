package com.fengdui.test.xhoa.business.app.web;

import com.fengdui.test.xhoa.business.app.entity.AppVersionInfo;
import com.fengdui.test.xhoa.business.app.service.AppVersionInfoService;
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

/**
 * @author fd
 * @date 2015年10月22日
 * @desc AppVersionInfoController.java
 */
@Controller
@RequestMapping("/app/appversion")
public class AppVersionInfoController extends BaseController<AppVersionInfo> {
	private static final Logger logger = LoggerFactory.getLogger(AppVersionInfoController.class);
	@Autowired
	private AppVersionInfoService appVersionInfoService;
	
	@Override
	protected Logger getLogger() {
		return logger;
	}

	@Override
	protected String getUrlMain() {
		return "/app/appversion";
	}

	@Override
	protected MybatisService getService() {
		return appVersionInfoService;
	}

	@Override
	protected AppVersionInfo prepareEntity() {
		return new AppVersionInfo();
	}
	@Override
	protected List<TableHeadInfo> buildTableHeadList() {
		List<TableHeadInfo> headList = new ArrayList<TableHeadInfo>();
		headList.add(new TableHeadInfo("center col-md-1", "", TableHeadInfo.TYPE_CHECKBOX));
		headList.add(new TableHeadInfo("center col-md-1", "序号"));
		headList.add(new TableHeadInfo("center col-md-1", "版本号"));
		headList.add(new TableHeadInfo("center col-md-1", "更新说明"));
		headList.add(new TableHeadInfo("center col-md-1", "更新文件"));
		headList.add(new TableHeadInfo("center col-md-1", "操作"));
		return headList;
	}
	@RequestMapping(value="/vaildVersion", method = RequestMethod.GET)
	@ResponseBody
	public Object vaildVersion(Integer versionNum) {
	
			if(appVersionInfoService.versionCheck(versionNum)) {
				return new PageRespData(true, Cue.SUCCESS);
			}
			return new PageRespData(false, Cue.FAIL);
	}
}
