package com.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
	/**
	 * 检查邮箱格式是否正确
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email){
	    Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
	    Matcher m = p.matcher(email);
	    return m.find();
	}
	
	/**
	 * 检查手机号格式是否正确
	 * @param mobile
	 * @return
	 */
	public static boolean checkMobile(String mobile){
	    Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
	    Matcher m = p.matcher(mobile);
	    return m.find();
	}

	public static boolean isIPAddress(String ipAddress) {
		Matcher matcher = _ipAddressPattern.matcher(ipAddress);

		return matcher.matches();
	}

	private static Pattern _ipAddressPattern = Pattern.compile("\\b"
			+ "((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\."
			+ "((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\."
			+ "((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\."
			+ "((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])" + "\\b");
}