package com.zheyue.encrypt;

import com.zheyue.encrypt.server.FileServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.Arrays;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = false, exposeProxy=true)
@EnableCaching
public class FileEncryptApplication {

	Logger LOGGER = LoggerFactory.getLogger(FileEncryptApplication.class);


	public static void main(String[] args) {
		Arrays.sort();
		System.out.println(3 / 2);
		SpringApplication application = new SpringApplication();
		application.setWebEnvironment(false);
		ConfigurableApplicationContext applicationContext = application.run(FileEncryptApplication.class, args);
		FileServer fileServer = (FileServer)applicationContext.getBean("fileServer");
		try {
			fileServer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
