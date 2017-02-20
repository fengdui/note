package com.fengdui.oa.business.res.web;

import com.fengdui.oa.business.res.entity.AppClass;
import com.fengdui.oa.business.res.service.AppClassService;
import com.fengdui.oa.framework.orm.MybatisService;
import com.fengdui.oa.framework.orm.PageFilter;
import com.fengdui.oa.framework.web.BaseController;
import com.fengdui.oa.framework.web.PageInfo;
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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Soda.Yang
 * @date 2015-9-22 上午11:02:25
 * @desc AppClassController.java
 */
@Controller
@RequestMapping("/res/appClass")
public class AppClassController extends BaseController<AppClass> {
	private static final Logger logger = LoggerFactory.getLogger(AppClassController.class);

	@Autowired
	private AppClassService appClassServie;

	@Override
	protected Logger getLogger() {
		return logger;
	}

	@Override
	protected String getUrlMain() {
		return "/res/appClass";
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected MybatisService getService() {
		return appClassServie;
	}

	@Override
	protected AppClass prepareEntity() {
		return new AppClass();
	}

	/**
	 * 存放数据供前台提取
	 * 
	 * @Title: editExtra
	 * @Description:
	 * @param valMap
	 */
	@Override
	protected void editExtra(Map<String, Object> valMap) {
		valMap.put("appClassParentList", appClassServie.getAppClassListByParentId(0));
	}

	@Override
	protected List<TableHeadInfo> buildTableHeadList() {
		List<TableHeadInfo> headList = new ArrayList<TableHeadInfo>();
		headList.add(new TableHeadInfo("center", "", TableHeadInfo.TYPE_CHECKBOX));
		headList.add(new TableHeadInfo("center col-md-1", "序号"));
		headList.add(new TableHeadInfo("center col-md-2", "名称"));
		headList.add(new TableHeadInfo("center col-md-2 ", "所属父级"));
		headList.add(new TableHeadInfo("center col-md-2", "描述"));
		headList.add(new TableHeadInfo("center col-md-2", "修改时间"));
		headList.add(new TableHeadInfo("center col-md-3", "操作"));
		return headList;
	}

	@Override
	protected AppClass editEntity(Integer id) {
		return appClassServie.get(id);
	}

	@Override
	protected void saveEntity(AppClass t) {
		// 获取父级名称
		if (0 == t.getParentId()) {
			t.setParentName("-");
		} else {
			AppClass appClass = appClassServie.getAppClassById(t.getParentId());
			t.setParentName(appClass.getName());
		}
		appClassServie.saveOrUpdate(t);
	}

	@Override
	protected void deleteEntity(List<Integer> idList) throws Exception {
		appClassServie.delete(idList);
	}

	/**
	 * 配置更多页面
	 * 
	 * @Title: prepareModel
	 * @Description:
	 * @return
	 */
	@Override
	protected Map<String, Object> prepareModel() {
		Map<String, Object> valMap = super.prepareModel();
		valMap.put("child", getUrlMain() + "/child");
		valMap.put("editAppClass", getUrlMain() + "/editAppClass");
		valMap.put("loadIndex", getUrlMain() + "/loadIndex");
		return valMap;
	}

	@RequestMapping(value = "/child/{parentId}", method = RequestMethod.POST)
	@ResponseBody
	protected Object loadChildrenList(HttpServletRequest request, PageInfo pageInfo, @PathVariable Integer parentId) {
		List<PageFilter> filterList = PageFilter.buildPageFilters(request);
		filterList.add(new PageFilter("parent_id = " + parentId));
		listExtraFilter(filterList);
		List<AppClass> list = appClassServie.queryByPage(pageInfo, filterList);
		listExtra(list);
		return new Object[] { pageInfo, list, listExtraObject(request) };
	}

	@Override
	protected void deletePrepare(List<Integer> idList) throws Exception {
		super.deletePrepare(idList);
		for (Integer it : idList) {
			appClassServie.deleteByValue("parent_id", it);
		}
	}

	@RequestMapping(value = "/editAppClass", method = RequestMethod.GET)
	public ModelAndView edit(Integer id, Integer parentId) {
		Map<String, Object> valMap = prepareModel();
		AppClass entity = prepareEntity();
		if (null != id) {
			entity = editEntity(id);
		} else {
			entity.setParentId(parentId);
			if (parentId != 0) {
				AppClass app = editEntity(parentId);
				entity.setParentName(app.getName());
			} else {
				entity.setParentName("-");
			}
		}
		valMap.put("entity", entity);
		if (null != id && parentId != 0) {
			editExtra(valMap);
		}
		return new ModelAndView(getUrlMain() + "_edit", valMap);
	}

	@RequestMapping(value = "/loadIndex", method = RequestMethod.GET)
	public ModelAndView loadindex(Integer parentId) {
		Map<String, Object> valMap = prepareModel();
		valMap.put("initParentId", parentId);
		valMap.put("headList", buildTableHeadList());
		indexExtra();
		return new ModelAndView(getUrlMain(), valMap);
	}

}
