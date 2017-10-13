package com.fengdui.dubbo;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.InvokerListener;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.listener.ListenerInvokerWrapper;
import com.fengdui.dubbo.service.IDubboDemoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Collections;

@SpringBootApplication
public class DubboClientApplication {

	public static void main(String[] args) {
//		SpringApplication.run(DubboClientApplication.class, args);
		SpringApplication application = new SpringApplication();

		ConfigurableApplicationContext applicationContext = application.run(DubboClientApplication.class, args);
		IDubboDemoService service = (IDubboDemoService)applicationContext.getBean("service");
		System.out.println(service.sayHello("fd"));
	}
}
