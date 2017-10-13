package com.fengdui.test.xhoa.framework.util.http;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Wander.Zeng
 * @data 2015-7-13 下午12:03:37
 * @desc HttpUtil.java
 */
public class HttpUtil {

	private static final String[] IP_HEADERS_TO_TRY = { "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP",
			"HTTP_CLIENT_IP", "HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR", "X-Real-IP" };

	/**
	 * 获取远程客户端 IP
	 */
	public static String getIPFromClient(HttpServletRequest request) {
		for (String header : IP_HEADERS_TO_TRY) {
			String ip = request.getHeader(header);
			if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
				return ip;
			}
		}
		return request.getRemoteAddr();
	}

	public static Map<String, Object> getParamMap(HttpServletRequest request, String prefix) {
		if (null == prefix) {
			prefix = "";
		}
		Enumeration<String> paramNames = request.getParameterNames();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if ("".equals(prefix) || paramName.startsWith(prefix)) {
				String[] values = request.getParameterValues(paramName);
				if (values == null || values.length == 0) {

				} else if (values.length > 1) {
					paramMap.put(paramName, values);
				} else {
					paramMap.put(paramName, values[0]);
				}
			}
		}
		return paramMap;
	}

	public static void main(String[] args) {

	}

}
