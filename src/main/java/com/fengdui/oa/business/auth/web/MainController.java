package com.fengdui.oa.business.auth.web;

import com.xh.market.business.sys.entity.Menu;
import com.xh.market.business.sys.entity.Role;
import com.xh.market.business.sys.service.MenuService;
import com.xh.market.framework.constant.ConstantSession;
import com.xh.market.framework.orm.MybatisService;
import com.xh.market.framework.util.http.WeatherUtil;
import com.xh.market.framework.web.BaseController;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Wander.Zeng
 * @data 2015-7-3 上午10:15:08
 * @desc MainController.java
 */
@Controller
@RequestMapping("/main")
public class MainController extends BaseController<Serializable> {

	private static final Logger logger = LoggerFactory.getLogger(MainController.class);

	@Autowired
	private MenuService menuService;

	@Override
	protected Logger getLogger() {
		return logger;
	}

	@Override
	protected String getUrlMain() {
		return "/main";
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected MybatisService getService() {
		return null;
	}

	@Override
	protected Serializable prepareEntity() {
		return null;
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request) {
		Map<String, Object> valMap = super.prepareModel();

		valMap.put("loginname", ConstantSession.getInstance().getLoginname());
		valMap.put("urlLogout", "logout");
		valMap.put("weatherInfo", WeatherUtil.getWeatherObj(request));

		//		List<NoticeMessage> messageList = new ArrayList<NoticeMessage>();
		//		messageList.add(new NoticeMessage("fa fa-phone bg-themeprimary white", "Skype meeting with Patty", "01:00 pm", "office"));
		//		messageList.add(new NoticeMessage("fa fa-phone bg-themeprimary white", "wander", "05:00 pm", "www"));
		//		valMap.put("messageList", messageList);
		//
		//		List<NoticeEmail> emailList = new ArrayList<NoticeEmail>();
		//		emailList.add(new NoticeEmail("/resources/image/icon.png", "Divyia Austin", "Here's the recipe for apple pie",
		//				"to identify the sending application when the senders image is shown for the main icon", DateUtil.newDate("2015-7-16 14:51:00", DateUtil.DATE_FORMAT_2)));
		//		emailList.add(new NoticeEmail("/resources/image/icon.png", "wander", "test", "wander", DateUtil.newDate("2015-7-16 9:00:00", DateUtil.DATE_FORMAT_2)));
		//		emailList.add(new NoticeEmail("/resources/image/icon.png", "www", "test", "www", DateUtil.newDate("2015-7-15 9:00:00", DateUtil.DATE_FORMAT_2)));
		//		valMap.put("emailList", emailList);
		//
		//		List<NoticeTask> taskList = new ArrayList<NoticeTask>();
		//		taskList.add(new NoticeTask("Account Creation", 65));
		//		taskList.add(new NoticeTask("wander", 55.5f));
		//		taskList.add(new NoticeTask("wander2", 55.1f));
		//		taskList.add(new NoticeTask("wander3", 55.9f));
		//		taskList.add(new NoticeTask("wander5", 59.9f));
		//		valMap.put("taskList", taskList);

		List<Menu> menuListBelongUser = menuService.getMenuListByUserId(ConstantSession.getInstance().getUser().getId());
		if (!SecurityUtils.getSubject().hasRole(String.valueOf(Role.ID_ROOT))) {
			for (int menuId : Menu.MENU_FILER) {
				menuListBelongUser.remove(new Menu(menuId));
			}
		}
		List<Menu> menuList = menuService.getMenuList4Sidebar(menuService.getRootMenu().getId(), menuListBelongUser);
		valMap.put("menuList", menuList);
		JSONObject menuObj = menuService.getMenuJsonObject(null, menuList);
		valMap.put("menuObj", menuService.buildMenuPath(menuObj));

		return new ModelAndView("auth/main", valMap);
	}

	@Override
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index() {
		return new ModelAndView("auth/main_index");
	}

}
