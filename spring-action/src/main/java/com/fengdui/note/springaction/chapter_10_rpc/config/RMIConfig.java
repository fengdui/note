package com.fengdui.note.springaction.chapter_10_rpc.config;


import com.fengdui.note.springaction.chapter_10_rpc.springRMI.HelloService;
import com.fengdui.note.springaction.chapter_10_rpc.springRMI.HelloServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;

import java.rmi.RemoteException;

/**
 * @author FD
 * @date 2017/1/20
 */

@Configuration
public class RMIConfig {


    @Bean
    public RmiServiceExporter rmiServiceExporter() {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setRegistryPort(8888);
        rmiServiceExporter.setServiceName("hello");
        rmiServiceExporter.setServiceInterface(HelloService.class);
        rmiServiceExporter.setService(new HelloServiceImpl());
        try {
            rmiServiceExporter.afterPropertiesSet();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return rmiServiceExporter;
    }
}
