package com.fengdui.test.xhoa.business.sys.web;

import com.fengdui.test.xhoa.business.auth.service.UserService;
import com.fengdui.test.xhoa.business.sys.entity.Role;
import com.fengdui.test.xhoa.business.sys.entity.Rolegroup;
import com.fengdui.test.xhoa.business.sys.service.MenuService;
import com.fengdui.test.xhoa.business.sys.service.RoleService;
import com.fengdui.test.xhoa.business.sys.service.RolegroupService;
import com.fengdui.test.xhoa.framework.constant.ConstantColumn;
import com.fengdui.test.xhoa.framework.constant.ConstantSession;
import com.fengdui.test.xhoa.framework.constant.Cue;
import com.fengdui.test.xhoa.framework.orm.MybatisService;
import com.fengdui.test.xhoa.framework.orm.PageFilter;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Wander.Zeng
 * @data 2015-8-1 上午10:47:24
 * @desc RoleController.java
 */
@Controller
@RequestMapping("/sys/role")
public class RoleController extends BaseController<Role> {

	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

	@Autowired
	private RoleService roleService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private UserService userService;
	@Autowired
	private RolegroupService rolegroupService;

	@Override
	protected Logger getLogger() {
		return logger;
	}

	@Override
	protected String getUrlMain() {
		return "/sys/role";
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected MybatisService getService() {
		return roleService;
	}

	@Override
	protected Role prepareEntity() {
		return new Role();
	}

	@Override
	public Map<String, Object> prepareModel() {
		Map<String, Object> valMap = super.prepareModel();
		valMap.put("urlGetMenuList", getUrlMain() + "/getMenuList");
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

	@Override
	protected List<TableHeadInfo> buildTableHeadList() {
		List<TableHeadInfo> headList = new ArrayList<TableHeadInfo>();
		headList.add(new TableHeadInfo("center", "", TableHeadInfo.TYPE_CHECKBOX));
		headList.add(new TableHeadInfo("center col-md-1", "序号"));
		headList.add(new TableHeadInfo("center col-md-2", "角色"));

		if (ConstantSession.getInstance().getUser().isRoot()) {
			List<Rolegroup> rolegroupSelList = new ArrayList<Rolegroup>();
			rolegroupSelList.add(new Rolegroup("全部角色组"));
			rolegroupSelList.addAll(rolegroupService.getList());
			headList.add(new TableHeadInfo("center col-md-2 rolegroupSelTH", null, TableHeadInfo.TYPE_SEL, "filter#EQ#rolegroup_id", rolegroupSelList));
		}

		headList.add(new TableHeadInfo("center col-md-1", "等级", TableHeadInfo.TYPE_SORT, "level"));
		if (ConstantSession.getInstance().getUser().isRoot()) {
			headList.add(new TableHeadInfo("center col-md-1", "描述"));
		} else {
			headList.add(new TableHeadInfo("center col-md-2", "描述"));
		}
		headList.add(new TableHeadInfo("center col-md-1", "包含账户数"));
		headList.add(new TableHeadInfo("center col-md-2", "修改时间", TableHeadInfo.TYPE_SORT, ConstantColumn.MODIFY_TIME));
		headList.add(new TableHeadInfo("center col-md-2", "操作"));
		return headList;
	}

	@Override
	protected void listExtraFilter(List<PageFilter> filterList) {
		if (ConstantSession.getInstance().getUser().isRoot()) {
			filterList.add(new PageFilter("id > " + Role.ID_ROOT));
		} else {
			filterList.add(new PageFilter("rolegroup_id = " + ConstantSession.getInstance().getUser().getRolegroupId()));
			filterList.add(new PageFilter("level > " + ConstantSession.getInstance().getUser().getRoleLevel()));
		}
	}

	@Override
	protected void listExtra(List<Role> list) {
		for (Role role : list) {
			role.setRolegroupName(rolegroupService.get(role.getRolegroupId()).getName());
			role.setCountUser(userService.getCountByRoleId(role.getId()));
		}
	}

	@Override
	protected Role editEntity(Integer id) {
		return roleService.getRole(id);
	}

	@Override
	protected void editExtra(Map<String, Object> valMap) {
		valMap.put("levelMin", ConstantSession.getInstance().getUser().getRoleLevel() + 1);
		valMap.put("levelMax", Role.LEVEL_MAX);
		valMap.put("rolegroupList", rolegroupService.getList());
	}

	@RequestMapping(value = "/getMenuList/{rolegroupId}", method = RequestMethod.POST)
	@ResponseBody
	public Object getMenuList(@PathVariable Integer rolegroupId) {
		try {
			short levelMin = (short) (ConstantSession.getInstance().getUser().getRoleLevel() + 1);
			short levelMax = Role.LEVEL_MAX;
			if (ConstantSession.getInstance().getUser().isRoot()) {
				return new PageRespData(true, Cue.SUCCESS, new Object[] { menuService.getMenuTreeByRolegroupId(rolegroupId), levelMin, levelMax });
			} else {
				return new PageRespData(true, Cue.SUCCESS, new Object[] { menuService.getMenuTreeByRoleId(ConstantSession.getInstance().getUser().getRoleId()), levelMin, levelMax });
			}
		} catch (Exception e) {
			getLogger().error("getMemuList error : " + e);
			return new PageRespData(false, Cue.FAIL, e.getMessage());
		}
	}

	@Override
	protected void saveEntity(Role role) {
		roleService.save(role);
	}

	@Override
	protected void deletePrepare(List<Integer> idList) throws Exception {
		if (idList.contains(Role.ID_ROOT)) {
			throw new Exception("角色【root】不能删除");
		}
	}

	@Override
	protected void deleteEntity(List<Integer> idList) {
		roleService.deleteRole(idList);
	}

}
