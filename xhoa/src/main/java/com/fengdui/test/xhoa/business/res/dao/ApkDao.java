package com.fengdui.test.xhoa.business.res.dao;

import com.fengdui.test.xhoa.business.res.entity.Apk;
import com.fengdui.test.xhoa.framework.orm.MybatisDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wander.Zeng
 * @data 2015-9-8 上午10:31:44
 * @desc ApkDao.java
 */
@Repository
public interface ApkDao extends MybatisDao<Apk, Integer> {
	
	public String getFileIdById(@Param("id") Integer id);
	
	public List<Apk> getListOnLib(@Param("packageName") String packageName);

}
