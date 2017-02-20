package com.fengdui.oa.business.sys.dao;

import com.fengdui.oa.business.sys.entity.Menu;
import com.fengdui.oa.framework.orm.MybatisDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wander.Zeng
 * @data 2015-7-17 上午11:36:26
 * @desc MenuDao.java
 */
@Repository
public interface MenuDao extends MybatisDao<Menu, Integer> {

	public Short getSeqMax(@Param("parentId") Integer parentId);

	public void batchChangeSeq(@Param("parentId") Integer parentId, @Param("seqBegin") Short seqBegin, @Param("step") Short step, @Param("modifyUser") String modifyUser);

	public List<Menu> getMenuListByRolegroupId(@Param("rolegroupId") Integer rolegroupId);
	
	public List<Menu> getMenuListByRoleId(@Param("roleId") Integer roleId);

	public List<Menu> getMenuListByUserId(@Param("userId") Integer userId);

}
