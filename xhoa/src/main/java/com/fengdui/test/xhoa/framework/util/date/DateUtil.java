package com.fengdui.test.xhoa.framework.util.date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Wander.Zeng
 * @data 2015-7-16 下午2:21:32
 * @desc DateUtil.java
 */
public class DateUtil {

	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

	/** yyyy-MM-dd */
	public static String DATE_FORMAT_1 = "yyyy-MM-dd";
	/** yyyy-MM-dd HH:mm:ss */
	public static String DATE_FORMAT_2 = "yyyy-MM-dd HH:mm:ss";
	/** yyyyMMddHHmm */
	public static String DATE_FORMAT_3 = "yyyyMMddHHmm";
	/** yyyy-MM-dd HH:mm */
	public static String DATE_FORMAT_4 = "yyyy-MM-dd HH:mm";
	/** yyyyMMdd */
	public static String DATE_FORMAT_5 = "yyyyMMdd";

	/**
	 * 取得当前日期
	 */
	public static Date currentDate() {
		return new Date(System.currentTimeMillis());
	}

	/**
	 * 获取秒级间隔
	 */
	public static int dateInterval4Sec(Date dateBegin, Date dateEnd) {
		try {
			SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT_2);
			long begin = df.parse(df.format(dateBegin)).getTime();
			long end = df.parse(df.format(dateEnd)).getTime();
			return (int) ((end - begin) / 1000);
		} catch (Exception e) {
			logger.error("dateInterval4Sec error : " + e);
			return 0;
		}
	}

	public static Date convertString2Date(String dateStr) {
		return convertString2Date(dateStr, DATE_FORMAT_2);
	}

	/**
	 * 将字符串转换成日期
	 */
	public static Date convertString2Date(String dateStr, String dateFormat) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			return sdf.parse(dateStr);
		} catch (Exception e) {
			logger.error("newDate error : " + e);
			return null;
		}
	}

	public static String convertDate2String(Date date) {
		return convertDate2String(date, DATE_FORMAT_2);
	}

	/**
	 * 将日期转换成字符串
	 */
	public static String convertDate2String(Date date, String dateFormat) {
		if (date == null) {
			return "";
		}
		if (StringUtils.isBlank(dateFormat)) {
			dateFormat = DATE_FORMAT_2;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(date);
	}

	public static void main(String[] args) {

	}

}
