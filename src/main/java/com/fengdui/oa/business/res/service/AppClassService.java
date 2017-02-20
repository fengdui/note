package com.fengdui.oa.business.res.service;

import com.xh.market.business.res.dao.AppClassDao;
import com.xh.market.business.res.entity.AppClass;
import com.xh.market.framework.orm.MybatisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Soda.Yang
 * @date 2015-9-22 上午11:02:25
 * @desc AppClassService.java
 */
@Service
public class AppClassService extends MybatisService<AppClass, Integer, AppClassDao> {

	@Autowired
	private AppClassDao appClassDao;

	public List<AppClass> getAppClassList() {
		return queryAll();
	}

	@Override
	protected AppClassDao getDao() {
		return appClassDao;
	}

	public AppClass getAppClassById(Integer id) {
		return get(id);

	}

	public List<AppClass> getAppClassListByParentId(Integer parentId) {
		return queryByValue("parent_id", parentId);
	}

}
