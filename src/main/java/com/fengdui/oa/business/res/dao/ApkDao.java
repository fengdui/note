package com.fengdui.oa.business.res.dao;

import com.xh.market.business.res.entity.Apk;
import com.xh.market.framework.orm.MybatisDao;
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
