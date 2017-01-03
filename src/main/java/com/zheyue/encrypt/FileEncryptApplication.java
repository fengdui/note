package com.zheyue.encrypt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FileEncryptApplication {


	private Thread thread = new Thread(new MyRunnable(), "fd");

	class MyRunnable implements Runnable {

		private volatile boolean stoped = false;

		@Override
		public void run() {
			while (!stoped) {
				try {
					System.out.println("你好");
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		System.out.println("你好");
		SpringApplication.run(FileEncryptApplication.class, args);
//		System.out.println("你好");
//		new FileEncryptApplication().thread.start();
//		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
//			@Override
//			public void run() {
//				System.out.println("绯荤粺閫�鍑�");
//			}
//		}));
	}

}
