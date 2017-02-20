package com.fengdui.oa.business.res.web;

import com.fengdui.oa.business.res.entity.App;
import com.fengdui.oa.business.res.service.ApkIconService;
import com.fengdui.oa.business.res.service.ApkService;
import com.fengdui.oa.business.res.service.AppService;
import com.fengdui.oa.framework.common.IdNamePair;
import com.fengdui.oa.framework.constant.ConstantColumn;
import com.fengdui.oa.framework.constant.Cue;
import com.fengdui.oa.framework.orm.MybatisService;
import com.fengdui.oa.framework.web.BaseController;
import com.fengdui.oa.framework.web.PageRespData;
import com.fengdui.oa.framework.web.TableHeadInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Wander.Zeng
 * @data 2015-9-24 上午10:45:35
 * @desc AppController.java
 */
@Controller
@RequestMapping("/res/app")
public class AppController extends BaseController<App> {

	private static final Logger logger = LoggerFactory.getLogger(AppController.class);

	@Autowired
	private AppService appService;
	@Autowired
	private ApkService apkService;
	@Autowired
	private ApkIconService apkIconService;

	@Override
	protected Logger getLogger() {
		return logger;
	}

	@Override
	protected String getUrlMain() {
		return "/res/app";
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected MybatisService getService() {
		return appService;
	}

	@Override
	protected App prepareEntity() {
		return new App();
	}

	@Override
	protected Map<String, Object> prepareModel() {
		Map<String, Object> valMap = super.prepareModel();
		valMap.put("urlChangeStatus", getUrlMain() + "/changeStatus");
		valMap.put("urlGetApkInfo", getUrlMain() + "/getApkInfo");
		return valMap;
	}

	@Override
	protected List<TableHeadInfo> buildTableHeadList() {
		List<TableHeadInfo> headList = new ArrayList<TableHeadInfo>();
		headList.add(new TableHeadInfo("center", "", TableHeadInfo.TYPE_CHECKBOX));
		headList.add(new TableHeadInfo("center col-md-1", "序号"));
		headList.add(new TableHeadInfo("center col-md-1", "图标"));
		headList.add(new TableHeadInfo("center col-md-2", "名称"));
		headList.add(new TableHeadInfo("center col-md-2", "包名"));

		List<IdNamePair> statusSelList = new ArrayList<IdNamePair>();
		statusSelList.add(new IdNamePair("全部状态"));
		statusSelList.addAll(Arrays.asList(App.STATUS_ARRAY));
		headList.add(new TableHeadInfo("center col-md-2 statusSelTH", null, TableHeadInfo.TYPE_SEL, "filter#EQ#status", statusSelList, 0));// 2

		headList.add(new TableHeadInfo("center col-md-2", "修改时间", TableHeadInfo.TYPE_SORT, ConstantColumn.MODIFY_TIME));
		headList.add(new TableHeadInfo("center col-md-2", "操作"));
		return headList;
	}

	@Override
	protected void listExtra(List<App> list) {
		for (App app : list) {
			app.setIconId(apkIconService.getDefaultIconFileId(app.getApkId()));
		}
	}

	@Override
	protected App editEntity(Integer id) {
		return appService.getApp(id);
	}

	@RequestMapping(value = "/edit4ApkManage", method = RequestMethod.GET)
	public ModelAndView edit4ApkManage(Integer id) {
		Map<String, Object> valMap = prepareModel();
		App entity = editEntity(id);
		valMap.put("entity", entity);
		valMap.put("urlExternal", "/res/apk/index");
		return new ModelAndView(getUrlMain() + "_edit", valMap);
	}

	@RequestMapping(value = "/edit4OnLine", method = RequestMethod.GET)
	public ModelAndView edit4OnLine(Integer apkId) {
		Map<String, Object> valMap = prepareModel();
		App entity = new App(apkService.get(apkId));
		valMap.put("entity", appService.addImageList(entity));
		valMap.put("urlExternal", "/res/apk/index");
		return new ModelAndView(getUrlMain() + "_edit", valMap);
	}

	@RequestMapping(value = "/getApkInfo/{apkId}", method = RequestMethod.POST)
	@ResponseBody
	public Object getApkInfo(@PathVariable Integer apkId) {
		try {
			return new PageRespData(true, Cue.SUCCESS, apkService.getApk(apkId));
		} catch (Exception e) {
			getLogger().error("getApkInfo error : " + e);
			return new PageRespData(false, Cue.FAIL, e.getMessage());
		}
	}

	@Override
	protected void saveEntity(App app) {
		appService.save(app);
	}

	@RequestMapping(value = "/changeStatus/{id}/{status}", method = RequestMethod.POST)
	@ResponseBody
	public Object changeStatus(@PathVariable Integer id, @PathVariable Integer status) {
		try {
			appService.changeStatus(id, status);
		} catch (Exception e) {
			getLogger().error("changeStatus error : " + e);
			return new PageRespData(false, Cue.FAIL, e.getMessage());
		}
		return new PageRespData(true, Cue.SUCCESS);
	}

	@Override
	protected void deleteEntity(List<Integer> idList) throws Exception {
		appService.deleteApp(idList);
	}

}
