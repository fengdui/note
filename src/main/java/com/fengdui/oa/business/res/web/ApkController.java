package com.fengdui.oa.business.res.web;

import com.xh.market.business.res.entity.Apk;
import com.xh.market.business.res.entity.ApkScreenshot;
import com.xh.market.business.res.service.ApkIconService;
import com.xh.market.business.res.service.ApkScreenshotService;
import com.xh.market.business.res.service.ApkService;
import com.xh.market.business.res.service.AppService;
import com.xh.market.framework.common.IdNamePair;
import com.xh.market.framework.constant.ConstantColumn;
import com.xh.market.framework.constant.Cue;
import com.xh.market.framework.orm.MybatisService;
import com.xh.market.framework.web.BaseController;
import com.xh.market.framework.web.PageRespData;
import com.xh.market.framework.web.TableHeadInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Wander.Zeng
 * @data 2015-9-8 上午10:22:09
 * @desc ApkController.java
 */
@Controller
@RequestMapping("/res/apk")
public class ApkController extends BaseController<Apk> {

	private static final Logger logger = LoggerFactory.getLogger(ApkController.class);

	@Autowired
	private ApkService apkService;
	@Autowired
	private ApkIconService apkIconService;
	@Autowired
	private ApkScreenshotService apkScreenshotService;
	@Autowired
	private AppService appService;

	@Override
	protected Logger getLogger() {
		return logger;
	}

	@Override
	protected String getUrlMain() {
		return "/res/apk";
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected MybatisService getService() {
		return apkService;
	}

	@Override
	protected Apk prepareEntity() {
		return new Apk();
	}

	@Override
	protected Map<String, Object> prepareModel() {
		Map<String, Object> valMap = super.prepareModel();
		valMap.put("urlApkUpload", getUrlMain() + "/apkUpload");
		valMap.put("urlApkHandle", getUrlMain() + "/apkHandle");
		valMap.put("urlScreenshotUpload", getUrlMain() + "/screenshotUpload");
		valMap.put("urlScreenshotHandle", getUrlMain() + "/screenshotHandle");
		valMap.put("urlOnLine", "/res/app/edit4OnLine");
		valMap.put("urlApkManage", "/res/app/edit4ApkManage");
		valMap.put("urlOnLib", getUrlMain() + "/edit4OnLib");
		valMap.put("urlOffLib", getUrlMain() + "/edit4OffLib");
		return valMap;
	}

	@Override
	protected List<TableHeadInfo> buildTableHeadList() {
		List<TableHeadInfo> headList = new ArrayList<TableHeadInfo>();
		headList.add(new TableHeadInfo("center", "", TableHeadInfo.TYPE_CHECKBOX));
		headList.add(new TableHeadInfo("center col-md-1", "序号"));
		headList.add(new TableHeadInfo("center col-md-1", "图标"));
		headList.add(new TableHeadInfo("center col-md-1", "名称"));
		headList.add(new TableHeadInfo("center col-md-1", "包名"));

		List<IdNamePair> statusSelList = new ArrayList<IdNamePair>();
		statusSelList.add(new IdNamePair("全部状态"));
		statusSelList.addAll(Arrays.asList(Apk.STATUS_ARRAY));
		headList.add(new TableHeadInfo("center col-md-1 statusSelTH", null, TableHeadInfo.TYPE_SEL, "filter#EQ#status", statusSelList, 0));// 1

		headList.add(new TableHeadInfo("center col-md-1", "版本"));
		headList.add(new TableHeadInfo("center col-md-1", "大小"));
		headList.add(new TableHeadInfo("center col-md-2", "修改时间", TableHeadInfo.TYPE_SORT, ConstantColumn.MODIFY_TIME));
		headList.add(new TableHeadInfo("center col-md-2", "操作"));
		return headList;
	}

	@Override
	protected void listExtra(List<Apk> list) {
		for (Apk apk : list) {
			apk.setIconId(apkIconService.getDefaultIconFileId(apk.getId()));
			apk.setScreenshotCount(apkScreenshotService.getCountByApkId(apk.getId()));
			apk.setApp(appService.getByPackageName(apk.getPackageName()));
		}
	}

	@Override
	protected Apk editEntity(Integer id) {
		return apkService.getApk(id);
	}

	@RequestMapping(value = "/edit4OnLib/{packageName}/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Object edit4OnLib(@PathVariable Integer id, @PathVariable String packageName) {
		try {
			apkService.onLib(id, packageName);
		} catch (Exception e) {
			getLogger().error("edit4OnLib error : " + e);
			return new PageRespData(false, Cue.FAIL, e.getMessage());
		}
		return new PageRespData(true, Cue.SUCCESS);
	}

	@RequestMapping(value = "/edit4OffLib/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Object edit4OffLib(@PathVariable Integer id) {
		try {
			apkService.offLibByApkId(id);
		} catch (Exception e) {
			getLogger().error("edit4OffLib error : " + e);
			return new PageRespData(false, Cue.FAIL, e.getMessage());
		}
		return new PageRespData(true, Cue.SUCCESS);
	}

	@RequestMapping(value = "/apkUpload", method = RequestMethod.POST)
	@ResponseBody
	public Object apkUpload(MultipartFile file) {
		String fileName = file.getOriginalFilename();
		if (!fileName.endsWith(".apk")) {
			return new PageRespData(false, "上传出错 : 后缀名不正确");
		}
		try {
			String filePath = apkService.importApk(file);
			return new PageRespData(true, Cue.SAVE_SUCCESS, filePath);
		} catch (Exception e) {
			getLogger().error("fileUpload error : " + e);
			return new PageRespData(false, "上传出错 : " + e.getMessage());
		}
	}

	@RequestMapping(value = "/apkHandle", method = RequestMethod.POST)
	@ResponseBody
	public Object apkHandle(String fileName, String filePath) {
		try {
			apkService.handleApk(fileName, filePath);
		} catch (Exception e) {
			getLogger().error("fileHandle error : " + e);
			return new PageRespData(false, "上传出错 : " + e.getMessage());
		}
		return new PageRespData(true, Cue.SAVE_SUCCESS);
	}

	@RequestMapping(value = "/screenshotUpload", method = RequestMethod.POST)
	@ResponseBody
	public Object screenshotUpload(MultipartFile file) {
		try {
			String filePath = apkService.importScreenshot(file);
			return new PageRespData(true, Cue.SAVE_SUCCESS, filePath);
		} catch (Exception e) {
			getLogger().error("screenshotUpload error : " + e);
			return new PageRespData(false, "上传出错 : " + e.getMessage());
		}
	}

	@RequestMapping(value = "/screenshotHandle", method = RequestMethod.POST)
	@ResponseBody
	public Object screenshotHandle(String filePath) {
		try {
			List<ApkScreenshot> screenFileIdList = apkService.handleScreenshot(filePath);
			return new PageRespData(true, Cue.SAVE_SUCCESS, screenFileIdList);
		} catch (Exception e) {
			getLogger().error("screenshotHandle error : " + e);
			return new PageRespData(false, "上传出错 : " + e.getMessage());
		}
	}

	@Override
	protected void saveEntity(Apk apk) {
		apkService.save(apk);
	}

	@Override
	protected void deleteEntity(List<Integer> idList) throws Exception {
		apkService.deleteApk(idList);
	}

}
