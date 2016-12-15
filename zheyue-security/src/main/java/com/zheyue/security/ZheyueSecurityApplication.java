package com.zheyue.security;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ZheyueSecurityApplication {

	public static void main(String[] args) {
//		SpringApplication.run(ZheyueSecurityApplication.class, args);
		SpringApplication application = new SpringApplication(ZheyueSecurityApplication.class);
//		application.setBannerMode(Banner.Mode.OFF);
		application.run(args);
	}
}
