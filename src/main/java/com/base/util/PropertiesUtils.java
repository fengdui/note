package com.base.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 操作Properties文件的工具类
 * @author suxl
 * @date 2014/03/24
 */
public class PropertiesUtils extends Properties {
	private static final long serialVersionUID = -6014767864187372870L;
	
	private static PropertiesUtils instance;
	
	//创建无参构造函数
	private PropertiesUtils(){
		InputStream is =this.getClass().getResourceAsStream("/config.properties");
		try {
			this.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//获取实例的方法
	public static PropertiesUtils getInstance(){
		//判断对象是否已被调用
		if(instance!=null)
		{
			return instance;
		}else{
			//重新创建一个实例，并返回该实例
			instance= new PropertiesUtils();
			return instance;
		}
	}
}
