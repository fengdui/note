package com.zheyue.rpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author FD
 * @data 2016/10/26
 */
@SpringBootApplication
public class RpcApplication {
    public static void main(String[] args) {
//        SpringApplication.run(RpcApplication.class, args);
//        SpringApplication app = new SpringApplication(RpcApplication.class);
//        app.setWebEnvironment(false);
//        app.run(args);
        String str6 = "b";
        String str7 = "a" + str6;
        String str67 = "ab";
        System.out.println("str7 = str67 : "+ (str7 == str67));
    }

}
