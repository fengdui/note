package com.fengdui.test.xhoa.business.res.dao;

import com.fengdui.test.xhoa.business.res.entity.ApkScreenshot;
import com.fengdui.test.xhoa.framework.orm.MybatisDao;
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
