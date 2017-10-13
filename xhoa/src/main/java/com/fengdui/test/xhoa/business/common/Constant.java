package com.fengdui.oa.business.common;

import com.fengdui.oa.framework.util.date.DateUtil;
import com.fengdui.oa.framework.util.file.FileUtil;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.UUID;

/**
 * @author Wander.Zeng
 * @data 2015-7-9 下午4:50:32
 * @desc Constant.java
 */
public class Constant {

	public static final String PATH_TMEP = "/temp";
	public static final String TEMP_FILE_APK = "apk";
	public static final String TEMP_FILE_IMG = "img";
	public static final String PATH_COMMOND = "/resources/command";

	public static String getRealPath(String path) {
		return ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath(path);
	}

	public static File saveTempFile(String fileType, MultipartFile fileSrc) throws Exception {
		String fileSrcName = fileSrc.getOriginalFilename();
		String fileDestName = UUID.randomUUID().toString().replaceAll("-", "") + fileSrcName.substring(fileSrcName.lastIndexOf("."));
		StringBuffer filePath = new StringBuffer();
		filePath.append(getRealPath(PATH_TMEP));
		filePath.append(File.separator);
		filePath.append(fileType);
		filePath.append(File.separator);
		filePath.append(DateUtil.convertDate2String(new Date(), DateUtil.DATE_FORMAT_5));
		filePath.append(File.separator);
		filePath.append(fileDestName);
		File fileDest = new File(filePath.toString());
		FileUtil.copy(fileSrc, fileDest);
		return fileDest;
	}

	public static File saveTempFile4Apk(MultipartFile fileSrc) throws Exception {
		return saveTempFile(TEMP_FILE_APK, fileSrc);
	}

	public static File saveTempFile4Screenshot(MultipartFile fileSrc) throws Exception {
		return saveTempFile(TEMP_FILE_IMG, fileSrc);
	}

}
