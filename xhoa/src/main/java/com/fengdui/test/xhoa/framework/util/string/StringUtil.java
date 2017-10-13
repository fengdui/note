package com.fengdui.oa.framework.util.string;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Wander.Zeng
 * @data 2015-7-2 下午7:19:10
 * @desc StringUtil.java
 */
public class StringUtil {

	/**
	 * 对object转义为数据库可以识别
	 */
	public static Object escapeObj4SQL(Object object) {
		if (object instanceof String) {
			String s = (String) object;
			object = s.replaceAll("'", "''");
		}
		if (object instanceof String || object instanceof Date || object instanceof Timestamp) {
			object = "'" + object + "'";
		}
		return object;
	}

	public static String convertListToString(List<String> list, String separator) {
		if (CollectionUtils.isEmpty(list)) {
			return "";
		}
		if (StringUtils.isBlank(separator)) {
			separator = ",";
		}
		StringBuffer sb = new StringBuffer("");
		int size = list.size();
		for (int i = 0; i < size; i++) {
			String val = list.get(i);
			if (!StringUtils.isBlank(val)) {
				sb.append(val);

				if (i != (size - 1)) {
					sb.append(separator);
				}
			}
		}
		return sb.toString();
	}

	public static List<Integer> convertStringToIntList(String strList, String separator) {
		List<Integer> list = new ArrayList<Integer>();
		if (StringUtils.isBlank(strList)) {
			return list;
		}
		String[] strArray = strList.split(separator);
		for (String str : strArray) {
			if (StringUtils.isNotBlank(str)) {
				list.add(Integer.parseInt(str.trim(), 10));
			}
		}
		return list;
	}

	public static Set<String> convertStringToStringSet(String strList, String separator) {
		Set<String> list = new HashSet<String>();
		if (StringUtils.isBlank(strList)) {
			return list;
		}
		String[] strArray = strList.split(separator);
		for (String str : strArray) {
			if (StringUtils.isNotBlank(str)) {
				list.add(str);
			}
		}
		return list;
	}

	public static String subString(String str, int length) {
		if (StringUtils.isBlank(str) || str.length() <= length) {
			return str;
		}
		return str.substring(0, length);
	}

	public static void main(String[] args) {

	}

}
