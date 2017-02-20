package com.fengdui.oa.business.sys.service;

import com.xh.market.business.sys.dao.RolegroupMenuRefDao;
import com.xh.market.business.sys.entity.RolegroupMenuRef;
import com.xh.market.framework.orm.MybatisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wander.Zeng
 * @data 2015-8-25 下午5:56:06
 * @desc RolegroupMenuRefService.java
 */
@Service
public class RolegroupMenuRefService extends MybatisService<RolegroupMenuRef, Integer, RolegroupMenuRefDao> {

	@Autowired
	private RolegroupMenuRefDao rolegroupMenuRefDao;

	@Override
	protected RolegroupMenuRefDao getDao() {
		return rolegroupMenuRefDao;
	}

	public String getMenuListStr(Integer rolegroupId) {
		return getDao().getMenuListStr(rolegroupId);
	}

	public List<Integer> getMenuList(Integer rolegroupId) {
		return getDao().getMenuList(rolegroupId);
	}

	public List<Integer> getRolegroupList(Integer menuId) {
		if (null == menuId) {
			return new ArrayList<Integer>();
		} else {
			return getDao().getRolegroupList(menuId);
		}
	}

	public int getCountByRolegroupIdAndMenuId(Integer rolegroupId, Integer menuId) {
		if (null == rolegroupId || null == menuId) {
			return 0;
		} else {
			return queryCountByMultiValue(new String[] { "rolegroup_id", "menu_id" }, new Object[] { rolegroupId, menuId });
		}
	}

	public void deleteByRolegroupId(int rolegroupId) {
		deleteByValue("rolegroup_id", rolegroupId);
	}

	public void deleteByMenuId(int menuId) {
		deleteByValue("menu_id", menuId);
	}

	public void deleteByRolegroupIdAndMenuId(int rolegroupId, int menuId) {
		deleteByMultiValue(new String[] { "rolegroup_id", "menu_id" }, new Object[] { rolegroupId, menuId });
	}

}
