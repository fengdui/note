package com.fengdui.test.xhoa.business.auth.web;

import com.fengdui.test.xhoa.business.auth.entity.User;
import com.fengdui.test.xhoa.business.auth.service.UserService;
import com.fengdui.test.xhoa.business.sys.entity.Role;
import com.fengdui.test.xhoa.business.sys.service.MenuService;
import com.fengdui.test.xhoa.business.sys.service.RoleService;
import com.fengdui.test.xhoa.business.sys.service.RolegroupService;
import com.fengdui.test.xhoa.framework.constant.ConstantColumn;
import com.fengdui.test.xhoa.framework.constant.ConstantSession;
import com.fengdui.test.xhoa.framework.constant.Cue;
import com.fengdui.test.xhoa.framework.orm.MybatisService;
import com.fengdui.test.xhoa.framework.web.BaseController;
import com.fengdui.test.xhoa.framework.web.PageRespData;
import com.fengdui.test.xhoa.framework.web.TableHeadInfo;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Wander.Zeng
 * @data 2015-8-12 下午3:12:54
 * @desc UserController.java
 */
@Controller
@RequestMapping("/auth/user")
public class UserController extends BaseController<User> {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private RolegroupService rolegroupService;
	@Autowired
	private MenuService menuService;

	@Override
	protected Logger getLogger() {
		return logger;
	}

	@Override
	protected String getUrlMain() {
		return "/auth/user";
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected MybatisService getService() {
		return userService;
	}

	@Override
	protected User prepareEntity() {
		return new User();
	}

	@Override
	public Map<String, Object> prepareModel() {
		Map<String, Object> valMap = super.prepareModel();
		valMap.put("urlGetRoleList", getUrlMain() + "/getRoleList");
		valMap.put("urlGetMenuList", getUrlMain() + "/getMenuList");
		return valMap;
	}

	@Override
	protected List<TableHeadInfo> buildTableHeadList() {
		List<TableHeadInfo> headList = new ArrayList<TableHeadInfo>();

		headList.add(new TableHeadInfo("center", "", TableHeadInfo.TYPE_CHECKBOX));
		headList.add(new TableHeadInfo("center col-md-1", "序号"));
		headList.add(new TableHeadInfo("center col-md-2", "登录名"));
		headList.add(new TableHeadInfo("center col-md-2", "用户名"));

		List<Role> roleSelList = new ArrayList<Role>();
		roleSelList.add(new Role("全部角色"));
		// roleSelList.addAll(roleService.getListByRoleLevel(ConstantSession.getInstance().getUser().getRoleLevel()));
		User userSession = ConstantSession.getInstance().getUser();
		if (userSession.isRoot()) {
			roleSelList.addAll(roleService.getListWithRolegroupName());
		} else {
			roleSelList.addAll(roleService.getListByLevelAndRolegroupId(userSession.getRoleLevel(), userSession.getRolegroupId()));
		}
		headList.add(new TableHeadInfo("center col-md-2 roleSelTH", null, TableHeadInfo.TYPE_SEL, "filter#EQ#role_id", roleSelList));

		headList.add(new TableHeadInfo("center col-md-2", "修改时间", TableHeadInfo.TYPE_SORT, "u." + ConstantColumn.MODIFY_TIME));
		headList.add(new TableHeadInfo("center col-md-2", "操作"));

		return headList;
	}

	@Override
	protected void listExtraFilter(List<PageFilter> filterList) {
		filterList.add(new PageFilter("u.deleted = FALSE"));
		filterList.add(new PageFilter("r.deleted = FALSE"));
		filterList.add(new PageFilter("r.level > " + ConstantSession.getInstance().getUser().getRoleLevel()));
		if (!ConstantSession.getInstance().getUser().isRoot()) {
			filterList.add(new PageFilter("u.parent_id = " + ConstantSession.getInstance().getUser().getId()));
			filterList.add(new PageFilter("r.rolegroup_id = " + ConstantSession.getInstance().getUser().getRolegroupId()));
		}
	}

	/*@Override
	protected void listExtra(List<User> list) {
		for (User user : list) {
			user.setRoleName(roleService.getNameById(user.getRoleId()));
		}
	}*/

	@Override
	protected User editEntity(Integer id) {
		return userService.getUser(id);
	}

	@Override
	protected void editExtra(Map<String, Object> valMap) {
		// valMap.put("roleList", roleService.queryAll());
		// valMap.put("roleList", roleService.getListByRoleLevel(ConstantSession.getInstance().getUser().getRoleLevel()));
		valMap.put("rolegroupList", rolegroupService.getList());
	}

	@RequestMapping(value = "/getRoleList/{rolegroupId}", method = RequestMethod.POST)
	@ResponseBody
	public Object getRoleList(@PathVariable Integer rolegroupId) {
		try {
			if (ConstantSession.getInstance().getUser().isRoot()) {
				return new PageRespData(true, Cue.SUCCESS, roleService.getListByRolegroupId(rolegroupId));
			} else {
				return new PageRespData(true, Cue.SUCCESS, roleService.getListByLevelAndRolegroupId(ConstantSession.getInstance().getUser().getRoleLevel(), rolegroupId));
			}
		} catch (Exception e) {
			getLogger().error("getRoleList error : " + e);
			return new PageRespData(false, Cue.FAIL, e.getMessage());
		}
	}

	@RequestMapping(value = "/getMenuList/{roleId}", method = RequestMethod.POST)
	@ResponseBody
	public Object getMenuList(@PathVariable Integer roleId) {
		try {
			if (ConstantSession.getInstance().getUser().isRoot()) {
				return new PageRespData(true, Cue.SUCCESS, menuService.getMenuTreeByRoleId(roleId));
			} else {
				return new PageRespData(true, Cue.SUCCESS, menuService.getMenuTreeByRoleIdAndUserId(roleId, ConstantSession.getInstance().getUser().getId()));
			}
		} catch (Exception e) {
			getLogger().error("getMemuList error : " + e);
			return new PageRespData(false, Cue.FAIL, e.getMessage());
		}
	}

	@Override
	protected void saveEntity(User user) {
		userService.save(user);
	}

	@Override
	protected void deleteEntity(List<Integer> idList) {
		userService.deleteUser(idList);
	}

	// 个人信息管理
	@RequestMapping(value = "/personal", method = RequestMethod.GET)
	public ModelAndView editPersonal() {
		Map<String, Object> valMap = new HashMap<String, Object>();
		valMap.put("urlPersonal", getUrlMain() + "/personal");
		valMap.put("urlSavePersonal", getUrlMain() + "/savePersonal");
		valMap.put("urlCheckPwd", getUrlMain() + "/checkPwd");
		valMap.put("urlLogout", "/tologout");
		valMap.put("entity", editEntity(ConstantSession.getInstance().getUser().getId()));
		return new ModelAndView(getUrlMain() + "_personal", valMap);
	}

	@RequestMapping(value = "/checkPwd", method = RequestMethod.POST)
	@ResponseBody
	public Object checkPwd(String pwdOriginal, String pwd2Check) {
		try {
			return new PageRespData(true, Cue.SUCCESS, userService.checkPassword(pwdOriginal, pwd2Check));
		} catch (Exception e) {
			getLogger().error("checkPwd error : " + e);
			return new PageRespData(false, Cue.FAIL, e.getMessage());
		}
	}

	@RequestMapping(value = "/savePersonal", method = RequestMethod.POST)
	@ResponseBody
	public Object savePersonal(User user) {
		try {
			saveEntity(user);
		} catch (Exception e) {
			getLogger().error("savePersonal error : " + e);
			return new PageRespData(false, Cue.SAVE_FAIL, e.getMessage());
		}
		return new PageRespData(true, Cue.SAVE_SUCCESS);
	}

}
