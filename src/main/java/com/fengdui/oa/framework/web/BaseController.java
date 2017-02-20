package com.fengdui.oa.framework.web;

import com.xh.market.framework.constant.Cue;
import com.xh.market.framework.orm.MybatisService;
import com.xh.market.framework.orm.PageFilter;
import com.xh.market.framework.util.string.StringUtil;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Wander.Zeng
 * @data 2015-6-16 上午11:43:24
 * @desc BaseController.java
 */
public abstract class BaseController<T extends Serializable> {

	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {

	}

	@ExceptionHandler({ UnauthorizedException.class })
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public ModelAndView handleException(NativeWebRequest request, Exception exception) {
		Map<String, Object> valMap = new HashMap<String, Object>();
		if (exception instanceof UnauthorizedException) {
			valMap.put("exceNameEn", "UnauthorizedException");
			valMap.put("exceNameCn", "授权异常");
		} else {
			valMap.put("exceNameEn", "UnknownException");
			valMap.put("exceNameCn", "未知异常");
		}
		valMap.put("exceDetail", exception.getMessage());
		return new ModelAndView("error/exception", valMap);
	}

	protected abstract Logger getLogger();

	protected abstract String getUrlMain();

	@SuppressWarnings("rawtypes")
	protected abstract MybatisService getService();

	protected abstract T prepareEntity();

	protected Map<String, Object> prepareModel() {
		Map<String, Object> valMap = new HashMap<String, Object>();
		valMap.put("urlIndex", getUrlMain() + "/index");
		valMap.put("urlList", getUrlMain() + "/list");
		valMap.put("urlEdit", getUrlMain() + "/edit");
		valMap.put("urlSave", getUrlMain() + "/save");
		valMap.put("urlDelete", getUrlMain() + "/delete");
		return valMap;
	}

	protected List<TableHeadInfo> buildTableHeadList() {
		return new ArrayList<TableHeadInfo>();
	}

	protected void indexExtra() {

	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index() {
		Map<String, Object> valMap = prepareModel();
		valMap.put("headList", buildTableHeadList());
		indexExtra();
		return new ModelAndView(getUrlMain(), valMap);
	}

	protected void listExtraFilter(List<PageFilter> filterList) {

	}

	protected void listExtra(List<T> list) {
		
	}

	protected Object listExtraObject(HttpServletRequest request) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Object list(HttpServletRequest request, PageInfo pageInfo) {
		List<PageFilter> filterList = PageFilter.buildPageFilters(request);
		listExtraFilter(filterList);
		List<T> list = getService().queryByPage(pageInfo, filterList);
		listExtra(list);
		return new Object[] { pageInfo, list, listExtraObject(request) };
	}

	@SuppressWarnings("unchecked")
	protected T editEntity(Integer id) {
		return (T) getService().get(id);
	}

	protected void editExtra(Map<String, Object> valMap) {

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(Integer id) {
		Map<String, Object> valMap = prepareModel();

		T entity = prepareEntity();
		if (null != id) {
			entity = editEntity(id);
		}
		valMap.put("entity", entity);
		editExtra(valMap);

		return new ModelAndView(getUrlMain() + "_edit", valMap);
	}

	@SuppressWarnings("unchecked")
	protected void saveEntity(T t) {
		getService().saveOrUpdate(t);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Object save(T t) {
		try {
			saveEntity(t);
		} catch (Exception e) {
			getLogger().error("save error : " + e);
			return new PageRespData(false, Cue.SAVE_FAIL, e.getMessage());
		}
		return new PageRespData(true, Cue.SAVE_SUCCESS);
	}

	protected void deletePrepare(List<Integer> idList) throws Exception {

	}

	@SuppressWarnings("unchecked")
	protected void deleteEntity(List<Integer> idList) throws Exception {
		getService().delete(idList);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Object delete(@PathVariable String id) {
		try {
			List<Integer> idList = StringUtil.convertStringToIntList(id, ",");
			deletePrepare(idList);
			deleteEntity(idList);
		} catch (Exception e) {
			getLogger().error("delete error : " + e);
			return new PageRespData(false, Cue.DEL_FAIL, e.getMessage());
		}
		return new PageRespData(true, Cue.DEL_SUCCESS);
	}

}
