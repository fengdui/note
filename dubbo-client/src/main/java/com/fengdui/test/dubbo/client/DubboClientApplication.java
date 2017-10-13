package com.fengdui.test.dubbo.client;

import com.fengdui.test.dubbo.client.service.IDubboDemoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DubboClientApplication {

	public static void main(String[] args) {
		//		SpringApplication.run(DubboClientApplication.class, args);
		SpringApplication application = new SpringApplication();

		ConfigurableApplicationContext applicationContext = application.run(DubboClientApplication.class, args);
		IDubboDemoService service = (IDubboDemoService)applicationContext.getBean("service");
		System.out.println(service.sayHello("fd"));
//		SpringApplication.run(DubboClientApplication.class, args);
	}
}
