package com.fengdui.oa.framework.util.file;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author Wander.Zeng
 * @data 2015-9-10 上午11:55:13
 * @desc FileUtil.java
 */
public class FileUtil {

	public static void copy(MultipartFile fileSrc, File fileDest) throws Exception {
		File fileDestParent = fileDest.getParentFile();
		if (fileDestParent != null && (!fileDestParent.exists())) {
			fileDestParent.mkdirs();
		}
		fileSrc.transferTo(fileDest);
	}

	/** 计算文件的MD5 */
	public static String getMD5(File file) throws Exception {
		return DigestUtils.md5Hex(new FileInputStream(file));
	}

}
