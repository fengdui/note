package com.fengdui.oa.business.auth.service;

import com.xh.market.business.auth.dao.UserDao;
import com.xh.market.business.auth.entity.User;
import com.xh.market.business.auth.entity.UserMenuRef;
import com.xh.market.business.sys.dao.RoleDao;
import com.xh.market.business.sys.entity.Role;
import com.xh.market.framework.constant.ConstantColumn;
import com.xh.market.framework.constant.ConstantCommon;
import com.xh.market.framework.constant.ConstantSession;
import com.xh.market.framework.orm.MybatisService;
import com.xh.market.framework.util.string.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wander.Zeng
 * @data 2015-7-2 下午1:51:51
 * @desc UserService.java
 */
@Service
public class UserService extends MybatisService<User, Integer, UserDao> {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserDao userDao;
	@Autowired
	private UserMenuRefService userMenuRefService;
	@Autowired
	private RoleDao roleDao;

	@Override
	protected UserDao getDao() {
		return userDao;
	}

	public User getUserByLoginname(String loginname) {
		User user = null;
		try {
			user = queryUniqueByValue("loginname", loginname);
		} catch (Exception e) {
			logger.error("getUserByLoginname error", e);
		}
		return user;
	}

	public String checkUser(String loginname, String password) {
		User userDB = getUser(loginname);
		if (null == userDB) {
			return "loginname is not exist" + ConstantCommon.SEPARATOR + "用户名不存在" + ConstantCommon.SEPARATOR + "loginname";
		} else {
			if (!password.equals(userDB.getPassword())) {
				return "password is wrong" + ConstantCommon.SEPARATOR + "密码错误" + ConstantCommon.SEPARATOR + "password";
			}
		}
		ConstantSession.getInstance().setUser(userDB);
		return null;
	}

	public boolean checkPassword(String pwdOriginal, String pwd2Check) {
		if (StringUtils.isBlank(pwd2Check) || StringUtils.isBlank(pwdOriginal)) {
			return false;
		} else {
			return User.encryptPWD(pwd2Check).equals(pwdOriginal);
		}
	}

	public User getUser(Integer id) {
		User user = get(id);
		user.setRolegroupId(roleDao.getRolegroupIdById(user.getRoleId()));
		user.setMenuListStr(userMenuRefService.getMenuListStr(id));
		if (StringUtils.isBlank(user.getMenuListStr())) {
			user.setMenuListStr("null");
		}
		return user;
	}

	public User getUser(String loginname) {
		User user = getUserByLoginname(loginname);
		if (null != user) {
			user.setMenuListStr(userMenuRefService.getMenuListStr(user.getId()));
			Role role = roleDao.queryUniqueByMultiValue(new String[] { ConstantColumn.ID, ConstantColumn.DELETED }, new Object[] { user.getRoleId(), false });
			user.setRoleName(role.getName());
			user.setRoleLevel(role.getLevel());
			user.setRolegroupId(role.getRolegroupId());
		}
		return user;
	}

	public void save(User user) {
		if (user.isNew()) {
			user.setParentId(ConstantSession.getInstance().getUser().getId());
			if (StringUtils.isBlank(user.getPasswordNew())) {
				user.setPasswordNew(User.DEFAULT_PASSWORD);
			}
			user.setPassword(User.encryptPWD(user.getPasswordNew()));
		} else {
			if (StringUtils.isNotBlank(user.getPasswordNew())) {
				user.setPassword(User.encryptPWD(user.getPasswordNew()));
			}
		}
		saveOrUpdate(user);
		saveMenuRef(user);
	}

	@SuppressWarnings("unchecked")
	private void saveMenuRef(User user) {
		String menuListStr = user.getMenuListStr();
		List<Integer> menuListOld = userMenuRefService.getMenuList(user.getId());
		List<Integer> menuListNew = StringUtil.convertStringToIntList(menuListStr, ",");
		List<Integer> menuListDel = (List<Integer>) CollectionUtils.subtract(menuListOld, menuListNew);
		List<Integer> menuListAdd = (List<Integer>) CollectionUtils.subtract(menuListNew, menuListOld);

		for (int menuId : menuListDel) {
			userMenuRefService.deleteByUserIdAndMenuId(user.getId(), menuId);
		}

		List<UserMenuRef> refListAdd = new ArrayList<UserMenuRef>();
		for (int menuId : menuListAdd) {
			refListAdd.add(new UserMenuRef(user.getId(), menuId));
		}
		if (refListAdd.size() > 0) {
			userMenuRefService.insertBatch(refListAdd);
		}
	}

	public int getCountByRoleId(int roleId) {
		return queryCountByValue("role_id", roleId);
	}

	public void deleteByRoleId(int roleId) {
		deleteByValue("role_id", roleId);
	}

	public void deleteUser(List<Integer> idList) {
		for (int id : idList) {
			delete(id);
			// 删除和菜单的关联
			userMenuRefService.deleteByUserId(id);
		}
	}

	public List<User> getListByRoleId(int roleId) {
		return queryByValue("role_id", roleId);
	}

}
