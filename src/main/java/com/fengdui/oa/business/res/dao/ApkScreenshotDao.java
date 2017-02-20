package com.fengdui.oa.business.res.dao;

import com.xh.market.business.res.entity.ApkScreenshot;
import com.xh.market.framework.orm.MybatisDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wander.Zeng
 * @data 2015-9-22 上午11:25:25
 * @desc ApkScreenshotDao.java
 */
@Repository
public interface ApkScreenshotDao extends MybatisDao<ApkScreenshot, Integer> {

	public List<String> getFileIdListByApkId(@Param("apkId") Integer apkId);

	public void deleteByApkIdAndSeq(@Param("apkId") Integer apkId, @Param("seq") Integer seq);

}
