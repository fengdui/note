package com.fengdui.test.dubbo.server;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@DubboComponentScan(basePackages = "com.fengdui.test.dubbo.server.service")
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
