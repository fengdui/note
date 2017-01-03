package com.zheyue.encrypt;

import com.zheyue.encrypt.server.FileServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class FileEncryptApplication {


	private Thread thread = new Thread(new MyRunnable(), "fd");

	class MyRunnable implements Runnable {

		private volatile boolean stoped = false;

		@Override
		public void run() {
			while (!stoped) {
				try {
					System.out.println("运行中。。。");
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
//		SpringApplication.run(FileEncryptApplication.class, args);
		SpringApplication application = new SpringApplication();
		application.setWebEnvironment(false);
		ConfigurableApplicationContext applicationContext = application.run(FileEncryptApplication.class, args);
		FileServer fileServer = (FileServer)applicationContext.getBean("fileServer");
		try {
			fileServer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
//		new FileEncryptApplication().thread.start();
//		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
//			@Override
//			public void run() {
//				System.out.println("系统退出");
//			}
//		}));
	}

}
