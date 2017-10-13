package com.fengdui.test.xhoa.business.sys.service;

import com.fengdui.test.xhoa.business.sys.dao.RolegroupDao;
import com.fengdui.test.xhoa.business.sys.entity.Rolegroup;
import com.fengdui.test.xhoa.business.sys.entity.RolegroupMenuRef;
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
 * @data 2015-8-25 下午4:24:33
 * @desc RolegroupService.java
 */
@Service
public class RolegroupService extends MybatisService<Rolegroup, Integer, RolegroupDao> {

	@Autowired
	private RolegroupDao rolegroupDao;
	@Autowired
	private RolegroupMenuRefService rolegroupMenuRefService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private RoleMenuRefService roleMenuRefService;

	@Override
	protected RolegroupDao getDao() {
		return rolegroupDao;
	}

	public Rolegroup getRolegroup(Integer id) {
		Rolegroup rolegroup = get(id);
		rolegroup.setMenuListStr(rolegroupMenuRefService.getMenuListStr(id));
		return rolegroup;
	}

	public void save(Rolegroup rolegroup) {
		saveOrUpdate(rolegroup);
		// 保存角色组和菜单的关联关系
		saveMenuRef(rolegroup);
	}

	@SuppressWarnings("unchecked")
	private void saveMenuRef(Rolegroup rolegroup) {
		String menuListStr = rolegroup.getMenuListStr();
		List<Integer> menuListOld = rolegroupMenuRefService.getMenuList(rolegroup.getId());
		List<Integer> menuListNew = StringUtil.convertStringToIntList(menuListStr, ",");
		List<Integer> menuListDel = (List<Integer>) CollectionUtils.subtract(menuListOld, menuListNew);
		List<Integer> menuListAdd = (List<Integer>) CollectionUtils.subtract(menuListNew, menuListOld);

		for (int menuId : menuListDel) {
			rolegroupMenuRefService.deleteByRolegroupIdAndMenuId(rolegroup.getId(), menuId);
			// 清除角色组对应的角色和账号与菜单的关联关系
			roleMenuRefService.deleteByRolegroupIdAndMenuId(rolegroup.getId(), menuId);
		}

		List<RolegroupMenuRef> refListAdd = new ArrayList<RolegroupMenuRef>();
		for (int menuId : menuListAdd) {
			refListAdd.add(new RolegroupMenuRef(rolegroup.getId(), menuId));
		}
		if (refListAdd.size() > 0) {
			rolegroupMenuRefService.insertBatch(refListAdd);
		}
	}

	public void deleteRolegroup(List<Integer> idList) {
		for (int id : idList) {
			delete(id);
			// 删除和菜单的关联
			rolegroupMenuRefService.deleteByRolegroupId(id);
			// 删除和角色的关联
			roleService.deleteByRolegroupId(id);
		}
	}

	public List<Rolegroup> getList() {
		return queryBySql(ConstantColumn.ID + " > " + Rolegroup.ID_ROOT_GROUP);
	}

}
