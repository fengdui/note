package com.fengdui.oa.business.sys.web;

import com.fengdui.oa.business.sys.entity.Role;
import com.fengdui.oa.business.sys.entity.Rolegroup;
import com.fengdui.oa.business.sys.service.MenuService;
import com.fengdui.oa.business.sys.service.RoleService;
import com.fengdui.oa.business.sys.service.RolegroupService;
import com.fengdui.oa.framework.constant.ConstantColumn;
import com.fengdui.oa.framework.orm.MybatisService;
import com.fengdui.oa.framework.orm.PageFilter;
import com.fengdui.oa.framework.web.BaseController;
import com.fengdui.oa.framework.web.TableHeadInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.security.auth.Subject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Wander.Zeng
 * @data 2015-8-25 下午4:28:00
 * @desc RolegroupController.java
 */
@Controller
@RequestMapping("/sys/rolegroup")
public class RolegroupController extends BaseController<Rolegroup> {

	private static final Logger logger = LoggerFactory.getLogger(RolegroupController.class);

	@Autowired
	private RolegroupService rolegroupService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private RoleService roleService;

	@Override
	protected Logger getLogger() {
		return logger;
	}

	@Override
	protected String getUrlMain() {
		return "/sys/rolegroup";
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected MybatisService getService() {
		return rolegroupService;
	}

	@Override
	protected Rolegroup prepareEntity() {
		return new Rolegroup();
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
		headList.add(new TableHeadInfo("center col-md-2", "角色组"));
		headList.add(new TableHeadInfo("center col-md-2", "描述"));
		headList.add(new TableHeadInfo("center col-md-2", "包含角色数"));
		headList.add(new TableHeadInfo("center col-md-2", "修改时间", TableHeadInfo.TYPE_SORT, ConstantColumn.MODIFY_TIME));
		headList.add(new TableHeadInfo("center col-md-2", "操作"));
		return headList;
	}

	@Override
	protected void listExtraFilter(List<PageFilter> filterList) {
		filterList.add(new PageFilter(ConstantColumn.ID + " > " + Rolegroup.ID_ROOT_GROUP));
	}

	@Override
	protected void listExtra(List<Rolegroup> list) {
		for (Rolegroup rolegroup : list) {
			rolegroup.setCountRole(roleService.getCountByRolegroupId(rolegroup.getId()));
		}
	}

	@Override
	protected Rolegroup editEntity(Integer id) {
		return rolegroupService.getRolegroup(id);
	}

	@Override
	protected void editExtra(Map<String, Object> valMap) {
		// valMap.put("menuTree", menuService.getMenuTreeAll());
		valMap.put("menuTree", menuService.getMenuTreeAllByFilter());
	}

	@Override
	protected void saveEntity(Rolegroup rolegroup) {
		rolegroupService.save(rolegroup);
	}

	@Override
	protected void deletePrepare(List<Integer> idList) throws Exception {
		if (idList.contains(Rolegroup.ID_ROOT_GROUP)) {
			throw new Exception("角色组【root组】不能删除");
		}
	}

	@Override
	protected void deleteEntity(List<Integer> idList) {
		rolegroupService.deleteRolegroup(idList);
	}

}
