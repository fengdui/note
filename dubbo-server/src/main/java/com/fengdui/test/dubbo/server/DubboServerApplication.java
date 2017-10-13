package com.fengdui.test.dubbo.server;

import com.alibaba.dubbo.config.spring.ReferenceBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DubboServerApplication {

	public static void main(String[] args) {
//		SpringApplication.run(DubboTestApplication.class, args);
		SpringApplication application = new SpringApplication();
		application.setWebEnvironment(false);
		ConfigurableApplicationContext applicationContext = application.run(DubboServerApplication.class, args);
//		while (true) {
//
//		}
	}
}
