package com.fengdui.oa.business.sys.service;

import com.xh.market.business.auth.service.UserMenuRefService;
import com.xh.market.business.sys.dao.RoleDao;
import com.xh.market.business.sys.dao.RoleMenuRefDao;
import com.xh.market.business.sys.entity.RoleMenuRef;
import com.xh.market.framework.orm.MybatisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wander.Zeng
 * @data 2015-8-10 下午7:24:33
 * @desc RoleMenuRefService.java
 */
@Service
public class RoleMenuRefService extends MybatisService<RoleMenuRef, Integer, RoleMenuRefDao> {

	@Autowired
	private RoleMenuRefDao roleMenuRefDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private UserMenuRefService userMenuRefService;

	@Override
	protected RoleMenuRefDao getDao() {
		return roleMenuRefDao;
	}

	public String getMenuListStr(Integer roleId) {
		return getDao().getMenuListStr(roleId);
	}

	public List<Integer> getMenuList(Integer roleId) {
		return getDao().getMenuList(roleId);
	}

	public List<Integer> getRoleList(Integer menuId) {
		if (null == menuId) {
			return new ArrayList<Integer>();
		} else {
			return getDao().getRoleList(menuId);
		}
	}

	public int getCountByRoleIdAndMenuId(Integer roleId, Integer menuId) {
		if (null == roleId || null == menuId) {
			return 0;
		} else {
			return queryCountByMultiValue(new String[] { "role_id", "menu_id" }, new Object[] { roleId, menuId });
		}
	}

	public void deleteByRoleId(int roleId) {
		deleteByValue("role_id", roleId);
	}

	public void deleteByMenuId(int menuId) {
		deleteByValue("menu_id", menuId);
	}

	public void deleteByRoleIdAndMenuId(int roleId, int menuId) {
		deleteByMultiValue(new String[] { "role_id", "menu_id" }, new Object[] { roleId, menuId });
	}

	public void deleteByRolegroupIdAndMenuId(int rolegroupId, int menuId) {
		List<Integer> roleIdList = roleDao.getIdListByRolegroupId(rolegroupId);
		for (int roleId : roleIdList) {
			deleteByRoleIdAndMenuId(roleId, menuId);
			userMenuRefService.deleteByRoleIdAndMenuId(roleId, menuId);
		}
	}

}
