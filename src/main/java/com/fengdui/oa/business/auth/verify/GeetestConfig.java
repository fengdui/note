package com.fengdui.oa.business.auth.verify;

/**
 * GeetestWeb配置文件
 * 
 * @author zheng
 * 
 */
public class GeetestConfig {

	// TODO: replace the these two string with your own captcha's id/key,the id/key below is just for demo
	private static final String captcha_id = "91ac94795b6c118af8f6e5589c5c21b3";// "4a0eb5e6f780944308954bc602814a53";// "91ac94795b6c118af8f6e5589c5c21b3";
	private static final String private_key = "3cee2c307f849a21d097d30808525f5c";// "03e0d35820a741d74445761c2b6d38c5";// "3cee2c307f849a21d097d30808525f5c";

	public static final String getCaptcha_id() {
		return captcha_id;
	}

	public static final String getPrivate_key() {
		return private_key;
	}

}
