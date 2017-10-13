package com.fengdui.test.xhoa.business.sys.web;

import com.fengdui.test.xhoa.business.sys.entity.Menu;
import com.fengdui.test.xhoa.business.sys.service.MenuService;
import com.fengdui.test.xhoa.framework.constant.Cue;
import com.fengdui.test.xhoa.framework.orm.MybatisService;
import com.fengdui.test.xhoa.framework.web.BaseController;
import com.fengdui.test.xhoa.framework.web.PageRespData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @author Wander.Zeng
 * @data 2015-7-20 下午4:17:30
 * @desc MenuController.java
 */
@Controller
@RequestMapping("/sys/menu")
public class MenuController extends BaseController<Menu> {

	private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

	@Autowired
	private MenuService menuService;

	@Override
	protected Logger getLogger() {
		return logger;
	}

	@Override
	protected String getUrlMain() {
		return "/sys/menu";
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected MybatisService getService() {
		return menuService;
	}

	@Override
	protected Menu prepareEntity() {
		return new Menu();
	}

	@Override
	public Map<String, Object> prepareModel() {
		Map<String, Object> valMap = super.prepareModel();
		valMap.put("urlListAjax", getUrlMain() + "/listAjax");
		valMap.put("urlListMore", getUrlMain() + "/listMore");
		valMap.put("urlEdit", getUrlMain() + "/edit/custom");
		valMap.put("urlChangePos", getUrlMain() + "/changePos");
		valMap.put("urlChangePos", getUrlMain() + "/changePos");
		valMap.put("urlGetMenuRef", getUrlMain() + "/getMenuRef");
		return valMap;
	}

	@Override
	protected void indexExtra() {
//		Subject currentUser = SecurityUtils.getSubject();
//		if (null != currentUser) {
//			if (!currentUser.hasRole(String.valueOf(Role.ID_ROOT))) {
//				throw new UnauthorizedException();
//			}
//		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(Integer id, Short level) {
		Map<String, Object> valMap = prepareModel();
		String menuTree = menuService.getMenuTree(id, level);
		valMap.put("menuTree", menuTree);
		return new ModelAndView("sys/menu_list", valMap);
	}

	@RequestMapping(value = "/listAjax", method = RequestMethod.POST)
	@ResponseBody
	public Object listAjax(Integer id, Short level) {
		try {
			return new PageRespData(true, Cue.SUCCESS, menuService.getMenuTreeObj(id, level));
		} catch (Exception e) {
			getLogger().error("listAjax error : " + e);
			return new PageRespData(false, Cue.FAIL, e.getMessage());
		}
	}

	@RequestMapping(value = "/listMore", method = RequestMethod.POST)
	@ResponseBody
	public Object listMore(Integer id) {
		return menuService.getChildMenuTree(id);
	}

	@RequestMapping(value = "/edit/custom", method = RequestMethod.GET)
	public ModelAndView edit(Integer parentId, Integer id, Short level) {
		Map<String, Object> valMap = prepareModel();
		Menu menu = new Menu();
		menu.setId(id);
		menu.setParentId(parentId);
		menu.setLevel(level);
		if (null != id) {
			menu = menuService.get(id);
			menu.setSubMenuCount(menuService.getChildMenuCount(id));
		}
		valMap.put("menu", menu);
		return new ModelAndView("sys/menu_edit", valMap);
	}

	@RequestMapping(value = "/getMenuRef", method = RequestMethod.POST)
	@ResponseBody
	public Object getMenuRef(Integer menuId) {
		try {
			return new PageRespData(true, Cue.SUCCESS, menuService.getMenuRefTreeByMenuId(menuId));
		} catch (Exception e) {
			getLogger().error("getMenuRef error : " + e);
			return new PageRespData(false, Cue.FAIL, e.getMessage());
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Object save(Menu menu) {
		try {
			menuService.save(menu);
		} catch (Exception e) {
			logger.error("save error : " + e);
			return new PageRespData(false, Cue.SAVE_FAIL, e.getMessage());
		}
		return new PageRespData(true, Cue.SAVE_SUCCESS, menu);
	}

	@Override
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Object delete(@PathVariable("id") String idStr) {
		Menu menu2Show = new Menu();
		try {
			int id = Integer.parseInt(idStr);
			menu2Show = menuService.getMenu2Show(id);
			menuService.deleteMenu(id);
		} catch (Exception e) {
			logger.error("delete error : " + e);
			return new PageRespData(false, Cue.DEL_FAIL, e.getMessage());
		}
		return new PageRespData(true, Cue.DEL_SUCCESS, menu2Show);
	}

	@RequestMapping(value = "/changePos/{id}/{targetId}/{moveType}", method = RequestMethod.POST)
	@ResponseBody
	public Object changePos(@PathVariable Integer id, @PathVariable Integer targetId, @PathVariable String moveType) {
		try {
			menuService.changePos(id, targetId, moveType);
		} catch (Exception e) {
			logger.error("change pos error : " + e);
			return new PageRespData(false, e.getMessage(), menuService.get(id));
		}
		return new PageRespData(true, Cue.SUCCESS, menuService.get(id));
	}

}
