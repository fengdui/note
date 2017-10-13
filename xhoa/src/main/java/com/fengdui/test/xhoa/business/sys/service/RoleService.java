package com.fengdui.test.xhoa.business.sys.service;

import com.fengdui.test.xhoa.business.auth.entity.User;
import com.fengdui.test.xhoa.business.auth.service.UserMenuRefService;
import com.fengdui.test.xhoa.business.auth.service.UserService;
import com.fengdui.test.xhoa.business.sys.dao.RoleDao;
import com.fengdui.test.xhoa.business.sys.dao.RolegroupDao;
import com.fengdui.test.xhoa.business.sys.entity.Role;
import com.fengdui.test.xhoa.business.sys.entity.RoleMenuRef;
import com.fengdui.test.xhoa.framework.constant.ConstantColumn;
import com.fengdui.test.xhoa.framework.orm.MybatisService;
import com.fengdui.test.xhoa.framework.util.string.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wander.Zeng
 * @data 2015-8-3 下午3:07:57
 * @desc RoleService.java
 */
@Service
public class RoleService extends MybatisService<Role, Integer, RoleDao> {

	// private static final Logger logger = LoggerFactory.getLogger(RoleService.class);

	@Autowired
	private RoleDao roleDao;
	@Autowired
	private RoleMenuRefService roleMenuRefService;
	@Autowired
	private UserService userService;
	@Autowired
	private RolegroupDao rolegroupDao;
	@Autowired
	private UserMenuRefService userMenuRefService;

	@Override
	protected RoleDao getDao() {
		return roleDao;
	}

	public Role getRole(Integer id) {
		Role role = get(id);
		role.setMenuListStr(roleMenuRefService.getMenuListStr(id));
		return role;
	}

	public void save(Role role) {
		saveOrUpdate(role);
		// 保存角色和菜单的关联关系
		saveMenuRef(role);
	}

	@SuppressWarnings("unchecked")
	private void saveMenuRef(Role role) {
		String menuListStr = role.getMenuListStr();
		List<Integer> menuListOld = roleMenuRefService.getMenuList(role.getId());
		List<Integer> menuListNew = StringUtil.convertStringToIntList(menuListStr, ",");
		List<Integer> menuListDel = (List<Integer>) CollectionUtils.subtract(menuListOld, menuListNew);
		List<Integer> menuListAdd = (List<Integer>) CollectionUtils.subtract(menuListNew, menuListOld);

		for (int menuId : menuListDel) {
			roleMenuRefService.deleteByRoleIdAndMenuId(role.getId(), menuId);
			// 清除角色对应的账号与菜单的关联关系
			userMenuRefService.deleteByRoleIdAndMenuId(role.getId(), menuId);
		}

		List<RoleMenuRef> refListAdd = new ArrayList<RoleMenuRef>();
		for (int menuId : menuListAdd) {
			refListAdd.add(new RoleMenuRef(role.getId(), menuId));
		}
		if (refListAdd.size() > 0) {
			roleMenuRefService.insertBatch(refListAdd);
		}
	}

	public void deleteRole(List<Integer> idList) {
		for (int id : idList) {
			delete(id);
			// 删除和菜单的关联
			roleMenuRefService.deleteByRoleId(id);
			// 删除角色对应的账号
			userService.deleteByRoleId(id);
		}
	}

	public void deleteByRolegroupId(int rolegroupId) {
		List<Integer> idList = getDao().getIdListByRolegroupId(rolegroupId);
		deleteRole(idList);
	}

	public String getNameById(Integer id) {
		return getDao().getNameById(id);
	}

	public List<Role> getListByRolegroupId(int rolegroupId) {
		// return queryByValue("rolegroup_id", rolegroupId);
		return queryByValueByOrder("rolegroup_id", rolegroupId, "level", ConstantColumn.ORDER_ASC);
	}

	public List<Role> getListByRoleLevel(short level) {
		return queryBySql("level > " + level);
	}

	public List<Role> getList() {
		return queryBySql(new String[] { ConstantColumn.ID + " > " + User.ID_ROOT }, new String[] { "rolegroup_id " + ConstantColumn.ORDER_ASC, "level " + ConstantColumn.ORDER_ASC });
	}

	public List<Role> getListByLevelAndRolegroupId(short level, int rolegroupId) {
		return queryBySql(new String[] { "level > " + level, "rolegroup_id = " + rolegroupId }, new String[] { "level " + ConstantColumn.ORDER_ASC });
	}

	public List<Role> getListWithRolegroupName() {
		List<Role> list = getList();
		for (Role role : list) {
			role.setName(rolegroupDao.getNameById(role.getRolegroupId()) + " - " + role.getName());
		}
		return list;
	}

	public int getCountByRolegroupId(int rolegroupId) {
		return queryCountByValue("rolegroup_id", rolegroupId);
	}

}
