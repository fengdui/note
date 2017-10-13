package com.fengdui;

import com.fengdui.chapter_10_rpc.springRMI.HelloService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class SpringInActionApplication {

	public static void main(String[] args) {
//		SpringApplication.run(SpringInActionApplication.class, args);
		SpringApplication application = new SpringApplication();
		application.setWebEnvironment(false);
		ConfigurableApplicationContext applicationContext = application.run(SpringInActionApplication.class, args);
		HelloService helloService = (HelloService)applicationContext.getBean("fd");
		helloService.sayHello("fd");
	}
}
