package com.fengdui.oa.business.res.dao;

import com.fengdui.oa.business.res.entity.ApkIcon;
import com.fengdui.oa.framework.orm.MybatisDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wander.Zeng
 * @data 2015-9-14 下午9:20:40
 * @desc ApkIconDao.java
 */
@Repository
public interface ApkIconDao extends MybatisDao<ApkIcon, Integer> {

	public String getIconFileId(@Param("apkId") Integer apkId, @Param("dpiType") Integer dpiType);

	public List<String> getIconFileIdListByApkId(@Param("apkId") Integer apkId);

}
