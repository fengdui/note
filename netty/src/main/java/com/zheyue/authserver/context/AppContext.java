package com.zheyue.authserver.context;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Simple wrapper class for the spring application context. It defines constants
 * configured in the spring xml configuration. This class can be used to
 * retrieve application beans using a String name.
 * 
 * @author Abraham Menacherry
 * 
 */
public class AppContext implements ApplicationContextAware {
	
	
	// The spring application context.
	private static ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		AppContext.applicationContext = applicationContext;
	}

	public static Object getBean(String beanName)
	{
		if (null == beanName) {
			return null;
		}
		return applicationContext.getBean(beanName);
	}
}
