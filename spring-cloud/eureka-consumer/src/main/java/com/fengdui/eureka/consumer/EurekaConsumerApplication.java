package com.fengdui.eureka.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@EnableEurekaClient
@SpringBootApplication
@RestController
public class EurekaConsumerApplication {

	@Autowired
	DiscoveryClient client;

	@RequestMapping("/fd")
	public String getUrl() {
		List<ServiceInstance> list = client.getInstances("server");
		if (list != null && list.size() > 0 ) {
			for (int i = 0; i < list.size(); i++) {
				URI uri = list.get(i).getUri();
				System.out.println(uri);
			}
			return list.get(0).getUri().toString();
		}
		return "没有";
	}
	public static void main(String[] args) {
		SpringApplication.run(EurekaConsumerApplication.class, args);
	}
}
