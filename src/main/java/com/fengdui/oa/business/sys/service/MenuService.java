package com.fengdui.oa.business.sys.service;

import com.fengdui.oa.business.auth.entity.User;
import com.fengdui.oa.business.auth.entity.UserMenuRef;
import com.fengdui.oa.business.auth.service.UserMenuRefService;
import com.fengdui.oa.business.auth.service.UserService;
import com.fengdui.oa.business.sys.dao.MenuDao;
import com.fengdui.oa.business.sys.entity.*;
import com.fengdui.oa.framework.constant.ConstantColumn;
import com.fengdui.oa.framework.constant.ConstantCommon;
import com.fengdui.oa.framework.constant.ConstantSession;
import com.fengdui.oa.framework.orm.MybatisService;
import com.fengdui.oa.framework.util.string.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Wander.Zeng
 * @data 2015-7-17 上午11:55:57
 * @desc MenuService.java
 */
@Service
public class MenuService extends MybatisService<Menu, Integer, MenuDao> {

	private static final Logger logger = LoggerFactory.getLogger(MenuService.class);

	@Autowired
	private MenuDao menuDao;
	@Autowired
	private RolegroupService rolegroupService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;
	@Autowired
	private RolegroupMenuRefService rolegroupMenuRefService;
	@Autowired
	private RoleMenuRefService roleMenuRefService;
	@Autowired
	private UserMenuRefService userMenuRefService;

	@Override
	protected MenuDao getDao() {
		return menuDao;
	}

	public Menu getRootMenu() {
		return queryUniqueByValue("level", Menu.LEVEL_ROOT);
	}

	public int getChildMenuCount(int parentId) {
		return queryCountByValue("parent_id", parentId);
	}

	public List<Menu> getChildMenuList(int parentId) {
		return queryByValueByOrder("parent_id", parentId, "seq", ConstantColumn.ORDER_ASC);
	}

	public List<Menu> getChildMenuListWithoutOrder(int parentId) {
		return queryByValue("parent_id", parentId);
	}

	public JSONArray getMenuTreeObj(Integer id, Short level) {
		JSONArray menuTree = new JSONArray();
		Menu rootMenu = getRootMenu();
		menuTree.add(buildMenuTreeObject(rootMenu, false));
		menuTree = buildMenuTreeArray(menuTree, rootMenu.getId());

		// 恢复编辑的菜单的展开状态
		if (null != level && level > 1) {
			Integer parentId = null;
			while (level > 1) {
				if (null == parentId) {
					parentId = get(id).getParentId();
				}
				List<Menu> childMenuList = getChildMenuList(parentId);
				for (Menu menu : childMenuList) {
					menuTree.add(buildMenuTreeObject(menu, false));
				}
				level--;
				if (level <= 1) {
					break;
				} else {
					parentId = get(parentId).getParentId();
				}
			}
		}

		return menuTree;
	}

	public String getMenuTree(Integer id, Short level) {
		String menuTreeStr = null;
		try {
			menuTreeStr = getMenuTreeObj(id, level).toString();
		} catch (Exception e) {
			logger.error("getMenuTree error : ", e);
		}
		return menuTreeStr;
	}

	public String getMenuTreeAll() {
		return getMenuTreeStr(queryByValueByOrder(ConstantColumn.DELETED, false, "seq", ConstantColumn.ORDER_ASC));
	}

	public String getMenuTreeAllByFilter() {
		List<Menu> list = queryByValueByOrder(ConstantColumn.DELETED, false, "seq", ConstantColumn.ORDER_ASC);
		for (int menuId : Menu.MENU_FILER) {
			list.remove(new Menu(menuId));
		}
		return getMenuTreeStr(list);
	}

	public String getMenuTreeStr(List<Menu> list) {
		String menuTreeStr = null;
		try {
			menuTreeStr = getMenuTree(list).toString();
		} catch (Exception e) {
			logger.error("getMenuTreeStr error : ", e);
		}
		return menuTreeStr;
	}

	public JSONArray getMenuTreeByRolegroupId(int rolegroupId) {
		return getMenuTree(getMenuListByRolegroupId(rolegroupId));
	}

	public JSONArray getMenuTreeByRoleId(int roleId) {
		return getMenuTree(getMenuListByRoleId(roleId));
	}

	public JSONArray getMenuRefTreeByMenuId(Integer menuId) {
		JSONArray menuRefTree = new JSONArray();
		try {
			List<Rolegroup> rolegroupList = rolegroupService.getList();
			for (Rolegroup rolegroup : rolegroupList) {
				JSONObject nodeRolegroup = new JSONObject();
				String idRolegroup = "rolegroup#" + rolegroup.getId();
				nodeRolegroup.put("id", idRolegroup);
				nodeRolegroup.put("pId", null);
				nodeRolegroup.put("name", rolegroup.getName());
				nodeRolegroup.put("idVal", rolegroup.getId());
				nodeRolegroup.put("type", "rolegroup");
				nodeRolegroup.put("checked", rolegroupMenuRefService.getCountByRolegroupIdAndMenuId(rolegroup.getId(), menuId) > 0);
				nodeRolegroup.put("open", true);
				List<Role> roleList = roleService.getListByRolegroupId(rolegroup.getId());
				nodeRolegroup.put("isParent", !((null == roleList || roleList.size() == 0)));
				menuRefTree.add(nodeRolegroup);

				for (Role role : roleList) {
					JSONObject nodeRole = new JSONObject();
					String idRole = "role#" + role.getId();
					nodeRole.put("id", idRole);
					nodeRole.put("pId", idRolegroup);
					nodeRole.put("name", role.getName());
					nodeRole.put("idVal", role.getId());
					nodeRole.put("type", "role");
					nodeRole.put("checked", roleMenuRefService.getCountByRoleIdAndMenuId(role.getId(), menuId) > 0);
					nodeRole.put("open", true);
					List<User> userList = userService.getListByRoleId(role.getId());
					nodeRole.put("isParent", !(null == userList || userList.size() == 0));
					menuRefTree.add(nodeRole);

					for (User user : userList) {
						JSONObject nodeUser = new JSONObject();
						String idUser = "user#" + user.getId();
						nodeUser.put("id", idUser);
						nodeUser.put("pId", idRole);
						nodeUser.put("name", user.getLoginname());
						nodeUser.put("idVal", user.getId());
						nodeUser.put("type", "user");
						nodeUser.put("checked", userMenuRefService.getCountByUserIdAndMenuId(user.getId(), menuId) > 0);
						nodeUser.put("open", false);
						nodeUser.put("isParent", false);
						menuRefTree.add(nodeUser);
					}
				}
			}
		} catch (Exception e) {
			logger.error("getMenuRefTreeByMenuId error : ", e);
		}
		return menuRefTree;
	}

	@SuppressWarnings("unchecked")
	public JSONArray getMenuTreeByRoleIdAndUserId(int roleId, int userId) {
		List<Menu> listByRoleId = getMenuListByRoleId(roleId);
		List<Menu> listByUserId = getMenuListByUserId(userId);
		List<Menu> listCommon = (List<Menu>) CollectionUtils.retainAll(listByRoleId, listByUserId);
		return getMenuTree(listCommon);
	}

	public JSONArray getMenuTree(List<Menu> list) {
		JSONArray menuTree = new JSONArray();
		try {
			for (Menu menu : list) {
				if (menu.getId() == Menu.LEVEL_ROOT) {
					continue;
				} else {
					menuTree.add(buildMenuTreeObject(menu, true));
				}
			}
		} catch (Exception e) {
			logger.error("getMenuTree error : ", e);
		}
		return menuTree;
	}

	public List<Menu> getMenuListByRolegroupId(int rolegroupId) {
		List<Menu> menuList = getDao().getMenuListByRolegroupId(rolegroupId);
		return completeMenuList(menuList);
	}

	public List<Menu> getMenuListByRoleId(int roleId) {
		List<Menu> menuList = getDao().getMenuListByRoleId(roleId);
		return completeMenuList(menuList);
	}

	public List<Menu> getMenuListByUserId(int userId) {
		List<Menu> menuList = getDao().getMenuListByUserId(userId);
		return completeMenuList(menuList);
	}

	private List<Menu> completeMenuList(List<Menu> list) {
		List<Menu> listFull = new ArrayList<Menu>();
		listFull.addAll(list);
		for (Menu menu : list) {
			Menu menuParent = get(menu.getParentId());
			while (null != menuParent && menuParent.getLevel() > Menu.LEVEL_ROOT) {
				if (!listFull.contains(menuParent)) {
					listFull.add(menuParent);
				}
				menuParent = get(menuParent.getParentId());
			}
		}
		return listFull;
	}

	public List<Menu> getMenuListByParentId(int parentId) {
		List<Menu> menuList = getChildMenuList(parentId);
		for (Menu menu : menuList) {
			menu.setSubMenuList(getMenuListByParentId(menu.getId()));
		}
		return menuList;
	}

	@SuppressWarnings("unchecked")
	public List<Menu> getMenuList4Sidebar(int parentId, List<Menu> menuListLimit) {
		List<Menu> menuList = getChildMenuList(parentId);
		menuList = (List<Menu>) CollectionUtils.retainAll(menuList, menuListLimit);
		for (Menu menu : menuList) {
			menu.setSubMenuList(getMenuList4Sidebar(menu.getId(), menuListLimit));
		}
		return menuList;
	}

	public JSONObject getMenuJsonObject(JSONObject jsonObject, List<Menu> menuList) {
		if (null == menuList || menuList.size() <= 0) {
			return jsonObject;
		}
		if (null == jsonObject) {
			jsonObject = new JSONObject();
		}

		for (Menu menu : menuList) {
			jsonObject = getMenuJsonObject(jsonObject, menu.getSubMenuList());
			int index = jsonObject.size() + 1;
			String key = String.valueOf(index);
			menu.setKey(key);
			jsonObject.put(key, new Menu(menu));
		}
		return jsonObject;
	}

	@SuppressWarnings("rawtypes")
	public JSONObject buildMenuPath(JSONObject menuListObj) {
		if (null == menuListObj) {
			return null;
		}
		Iterator it = menuListObj.keys();
		while (it.hasNext()) {
			String menukey = (String) it.next();
			JSONObject menuObj = menuListObj.getJSONObject(menukey);
			int parentId = menuObj.getInt("parentId");
			short level = (short) menuObj.getInt("level");
			while (level > (Menu.LEVEL_ROOT + 1)) {
				JSONObject menuObjParent = getMenuObjById(menuListObj, parentId);
				String path = "";
				if (menuObj.has("path")) {
					path = menuObjParent.getString("name") + ConstantCommon.SEPARATOR + menuObj.getString("path");
				} else {
					path = menuObjParent.getString("name");
				}
				menuObj.put("path", path);
				parentId = menuObjParent.getInt("parentId");
				level = (short) menuObjParent.getInt("level");
			}
		}
		return menuListObj;
	}

	@SuppressWarnings("rawtypes")
	private JSONObject getMenuObjById(JSONObject menuListObj, int id) {
		Iterator it = menuListObj.keys();
		while (it.hasNext()) {
			String menukey = (String) it.next();
			JSONObject menuObj = menuListObj.getJSONObject(menukey);
			int menuId = menuObj.getInt("id");
			if (menuId == id) {
				return menuObj;
			}
		}
		return null;
	}

	public JSONArray getChildMenuTree(Integer id) {
		JSONArray menuTree = null;
		try {
			menuTree = buildMenuTreeArray(new JSONArray(), id);
		} catch (Exception e) {
			logger.error("getChildMenuTree error : ", e);
		}
		return menuTree;
	}

	private JSONObject buildMenuTreeObject(Menu menu, boolean simpleData) {
		boolean isRootMenu = menu.getLevel() == Menu.LEVEL_ROOT ? true : false;
		JSONObject rootNode = new JSONObject();
		rootNode.put("id", menu.getId());
		rootNode.put("pId", menu.getParentId());
		if (simpleData) {
			rootNode.put("name", menu.getName());
		} else {
			rootNode.put("name", "<span class='editMenu'><i class=\"" + menu.getIcon() + "\"></i> " + menu.getName() + "</span>");
			rootNode.put("fullName", menu.getName());
		}
		rootNode.put("open", true);
		rootNode.put("isParent", queryCountByValue("parent_id", menu.getId()) > 0 ? true : false);
		if (isRootMenu) {
			rootNode.put("drag", false);
			rootNode.put("drop", false);
			// rootNode.put("iconOpen", "/resources/plugin/zTree_v3/css/zTreeStyle/img/diy/1_open.png");
			// rootNode.put("iconClose", "/resources/plugin/zTree_v3/css/zTreeStyle/img/diy/1_close.png");
		}
		return rootNode;
	}

	private JSONArray buildMenuTreeArray(JSONArray obj, Integer id) {
		List<Menu> childMenuList = getChildMenuList(id);
		for (Menu menu : childMenuList) {
			obj.add(buildMenuTreeObject(menu, false));
		}
		return obj;
	}

	public void save(Menu menu) throws Exception {
		if (menu.getLevel() > Menu.LEVEL_MAX) {
			throw new Exception("最多只能创建" + Menu.LEVEL_MAX + "个级别菜单");
		}
		if (menu.isNew()) {
			menu.setSeq(getSeqNext(menu.getParentId()));
		}
		saveOrUpdate(menu);
		if (menu.isChangeRef()) {
			if (StringUtils.isNotBlank(menu.getRefListStr())) {
				saveRef(menu.getId(), menu.getRefListStr().split("#"));
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void saveRef(Integer menuId, String[] refArray) {
		List<Integer> rolegroupIdListOld = rolegroupMenuRefService.getRolegroupList(menuId);
		List<Integer> rolegroupIdListNew = StringUtil.convertStringToIntList(refArray[0], ",");
		List<Integer> rolegroupIdListDel = (List<Integer>) CollectionUtils.subtract(rolegroupIdListOld, rolegroupIdListNew);
		List<Integer> rolegroupIdListAdd = (List<Integer>) CollectionUtils.subtract(rolegroupIdListNew, rolegroupIdListOld);
		for (int rolegroupId : rolegroupIdListDel) {
			rolegroupMenuRefService.deleteByRolegroupIdAndMenuId(rolegroupId, menuId);
		}
		for (int rolegroupId : rolegroupIdListAdd) {
			rolegroupMenuRefService.insert(new RolegroupMenuRef(rolegroupId, menuId));
		}

		List<Integer> roleIdListOld = roleMenuRefService.getRoleList(menuId);
		List<Integer> roleIdListNew = StringUtil.convertStringToIntList(refArray[1], ",");
		List<Integer> roleIdListDel = (List<Integer>) CollectionUtils.subtract(roleIdListOld, roleIdListNew);
		List<Integer> roleIdListAdd = (List<Integer>) CollectionUtils.subtract(roleIdListNew, roleIdListOld);
		for (int roleId : roleIdListDel) {
			roleMenuRefService.deleteByRoleIdAndMenuId(roleId, menuId);
		}
		for (int roleId : roleIdListAdd) {
			roleMenuRefService.insert(new RoleMenuRef(roleId, menuId));
		}

		List<Integer> userIdListOld = userMenuRefService.getUserList(menuId);
		List<Integer> userIdListNew = StringUtil.convertStringToIntList(refArray[2], ",");
		List<Integer> userIdListDel = (List<Integer>) CollectionUtils.subtract(userIdListOld, userIdListNew);
		List<Integer> userIdListAdd = (List<Integer>) CollectionUtils.subtract(userIdListNew, userIdListOld);
		for (int userId : userIdListDel) {
			userMenuRefService.deleteByUserIdAndMenuId(userId, menuId);
		}
		for (int userId : userIdListAdd) {
			userMenuRefService.insert(new UserMenuRef(userId, menuId));
		}
	}

	private Short getSeqNext(Integer parentId) {
		Short seq = getDao().getSeqMax(parentId);
		if (null == seq) {
			return 0;
		} else {
			seq++;
			return seq;
		}
	}

	/** 获取删除后需要显示的菜单 */
	public Menu getMenu2Show(Integer id) {
		Menu menu = get(id);
		List<Menu> list = getChildMenuList(menu.getParentId());
		if (list.size() > 1) {
			for (Menu m : list) {
				if (m.getId() != id) {
					return m;
				}
			}
			return null;
		} else {
			return get(menu.getParentId());
		}
	}

	public void deleteMenu(Integer id) {
		Menu menu = get(id);
		getDao().batchChangeSeq(menu.getParentId(), (short) (menu.getSeq() + 1), (short) -1, ConstantSession.getInstance().getLoginname());
		deleteRecursive(id);
	}

	public void deleteRecursive(Integer id) {
		List<Menu> menuList = getChildMenuListWithoutOrder(id);
		for (Menu menu : menuList) {
			deleteRecursive(menu.getId());
		}
		delete(id);
		// 删除和角色的关联
		roleMenuRefService.deleteByMenuId(id);
		// 删除和账号的关联
		userMenuRefService.deleteByMenuId(id);
	}

	public void changePos(Integer id, Integer targetId, String moveType) {
		Menu menu = get(id);
		Menu menuTarget = get(targetId);

		if (null != menu && null != menuTarget) {
			if ("inner".equals(moveType)) {
				short levelDiff = (short) (menuTarget.getLevel() + 1 - menu.getLevel());
				changeLevel(menu, levelDiff);
				// 把原来所在菜单后面的菜单排序
				getDao().batchChangeSeq(menu.getParentId(), menu.getSeq(), (short) -1, ConstantSession.getInstance().getLoginname());
				// 放在第一个
				// getDao().batchChangeSeq(menuTarget.getId(), (short)0, (short)1, ConstantSession.getInstance().getLoginname());
				// updateMultiValueByValue(new String[] { "parent_id", "seq" }, new Object[] { menuTarget.getId(), 0 }, "id", menu.getId());
				// 放在最后一个
				updateMultiValueByValue(new String[] { "parent_id", "seq" }, new Object[] { menuTarget.getId(), getSeqNext(menuTarget.getId()) }, "id", menu.getId());
			} else if ("prev".equals(moveType)) {
				short levelDiff = (short) (menuTarget.getLevel() - menu.getLevel());
				if (levelDiff != 0) {
					changeLevel(menu, levelDiff);
				}
				// 把原来所在菜单后面的菜单排序
				getDao().batchChangeSeq(menu.getParentId(), menu.getSeq(), (short) -1, ConstantSession.getInstance().getLoginname());
				short seqTarget = menuTarget.getSeq();
				// 把目标菜单所在的菜单后面的菜单排序
				getDao().batchChangeSeq(menuTarget.getParentId(), menuTarget.getSeq(), (short) 1, ConstantSession.getInstance().getLoginname());
				updateMultiValueByValue(new String[] { "parent_id", "seq" }, new Object[] { menuTarget.getParentId(), seqTarget }, "id", menu.getId());
			} else if ("next".equals(moveType)) {
				short levelDiff = (short) (menuTarget.getLevel() - menu.getLevel());
				if (levelDiff != 0) {
					changeLevel(menu, levelDiff);
				}
				// 把原来所在菜单后面的菜单排序
				getDao().batchChangeSeq(menu.getParentId(), menu.getSeq(), (short) -1, ConstantSession.getInstance().getLoginname());
				menuTarget = get(targetId);
				short seqTarget = (short) (menuTarget.getSeq() + 1);
				// 把目标菜单所在的菜单后面的菜单排序
				getDao().batchChangeSeq(menuTarget.getParentId(), seqTarget, (short) 1, ConstantSession.getInstance().getLoginname());
				updateMultiValueByValue(new String[] { "parent_id", "seq" }, new Object[] { menuTarget.getParentId(), seqTarget }, "id", menu.getId());
			}
		}
	}

	private void changeLevel(Menu menu, short levelDiff) {
		if ((menu.getLevel() + levelDiff) > Menu.LEVEL_MAX) {
			throw new RuntimeException("菜单级别超出" + Menu.LEVEL_MAX + "级");
		}
		List<Menu> menuList = getChildMenuListWithoutOrder(menu.getId());
		for (Menu m : menuList) {
			changeLevel(m, levelDiff);
		}
		updateValueByValue("level", menu.getLevel() + levelDiff, "id", menu.getId());
	}

}
