package com.fengdui.test.zookeeper;


import java.util.Random;

//@SpringBootApplication
public class ZookeeperApplication {

	static class A {
		Integer score;

		public A(Integer score) {
			this.score = score;
		}

		public Integer getScore() {
			return score;
		}

		public void setScore(Integer score) {
			this.score = score;
		}
	}
	public static void main(String[] args) {
//		SpringApplication.run(ZookeeperApplication.class, args);
		for (int i = 0; i < 10000; i++) {
			A a = new A(new Random().nextInt(100));
			Integer score = a.getScore();
			Integer resScore = getScore(score);
			a.setScore(resScore);
			if (a.getScore() - score != 10) {
				System.out.println("xxx");
			}
		}
	}
	public static Integer getScore(Integer score) {
		return score + 10;
	}
}
