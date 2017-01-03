package com.zheyue.encrypt;

import com.zheyue.encrypt.server.FileServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class FileEncryptApplication {

	public static void main(String[] args) {

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
