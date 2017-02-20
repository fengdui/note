package com.fengdui.oa.business.auth.service;

import com.xh.market.business.auth.dao.UserDao;
import com.xh.market.business.auth.dao.UserMenuRefDao;
import com.xh.market.business.auth.entity.UserMenuRef;
import com.xh.market.framework.orm.MybatisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wander.Zeng
 * @data 2015-8-18 下午5:27:24
 * @desc UserMenuRefService.java
 */
@Service
public class UserMenuRefService extends MybatisService<UserMenuRef, Integer, UserMenuRefDao> {

	@Autowired
	private UserMenuRefDao userMenuRefDao;
	@Autowired
	private UserDao userDao;

	@Override
	protected UserMenuRefDao getDao() {
		return userMenuRefDao;
	}

	public String getMenuListStr(Integer userId) {
		return getDao().getMenuListStr(userId);
	}

	public List<Integer> getMenuList(Integer userId) {
		return getDao().getMenuList(userId);
	}

	public List<Integer> getUserList(Integer menuId) {
		if (null == menuId) {
			return new ArrayList<Integer>();
		} else {
			return getDao().getUserList(menuId);
		}
	}

	public int getCountByUserIdAndMenuId(Integer userId, Integer menuId) {
		if (null == userId || null == menuId) {
			return 0;
		} else {
			return queryCountByMultiValue(new String[] { "user_id", "menu_id" }, new Object[] { userId, menuId });
		}
	}

	public void deleteByUserId(int userId) {
		deleteByValue("user_id", userId);
	}

	public void deleteByMenuId(int menuId) {
		deleteByValue("menu_id", menuId);
	}

	public void deleteByUserIdAndMenuId(int userId, int menuId) {
		deleteByMultiValue(new String[] { "user_id", "menu_id" }, new Object[] { userId, menuId });
	}

	public void deleteByRoleIdAndMenuId(int roleId, int menuId) {
		List<Integer> userIdList = userDao.getIdListByRoleId(roleId);
		for (int userId : userIdList) {
			deleteByUserIdAndMenuId(userId, menuId);
		}
	}

}
