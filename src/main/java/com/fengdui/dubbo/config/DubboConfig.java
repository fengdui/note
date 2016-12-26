package com.fengdui.dubbo.config;

import com.alibaba.dubbo.config.*;
import com.alibaba.dubbo.config.spring.AnnotationBean;
import com.alibaba.dubbo.config.spring.ReferenceBean;
import com.fengdui.dubbo.service.IDubboDemoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.beans.PropertyDescriptor;

/**
 * @author FD
 * @date 2016/12/20
 */
@Configuration
//@PropertySource(value = "classpath:/dubbo.properties")
public class DubboConfig {

    @Value("${dubbo.application.name}")
    private String applicationName;

//    @Value("${dubbo.application.logger}")
//    private String logger;

    @Value("${dubbo.registr.protocol}")
    private String protocol;

    @Value("${dubbo.registry.address}")
    private String registryAddress;

    @Value("${dubbo.protocol.name}")
    private String protocolName;

    @Value("${dubbo.protocol.port}")
    private int protocolPort;

    @Value("${dubbo.provider.timeout}")
    private int timeout;

    @Value("${dubbo.provider.retries}")
    private int retries;

    @Value("${dubbo.provider.delay}")
    private int delay;

    @Value("${dubbo.annotation.package}")
    private String packageName;


//    @Bean
//    public ReferenceBean referenceBean(ApplicationConfig application, RegistryConfig registry) {
//        // 引用远程服务
//        ReferenceBean<IDubboDemoService> reference = new ReferenceBean<IDubboDemoService>(); // 此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
//        reference.setApplication(application);
//        reference.setRegistry(registry); // 多个注册中心可以用setRegistries()
//        reference.setInterface(IDubboDemoService.class);
//        reference.setVersion("1.0.0");
//        // 和本地bean一样使用xxxService
////        IDubboDemoService xxxService = reference.get(); // 注意：此代理对象内部封装了所有通讯细节，对象较重，请缓存复用
//        return reference;
//    }


    /**
     * 注入dubbo上下文
     * @return
     */
    @Bean
    public ApplicationConfig applicationConfig() {
        System.out.println(delay);
        // 当前应用配置
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(this.applicationName);
        return applicationConfig;
    }

    @Bean
    public static AnnotationBean annotationBean(@Value("${dubbo.annotation.package}") String packageName) {

        AnnotationBean annotationBean = new AnnotationBean();
        annotationBean.setPackage(packageName);
        return annotationBean;
    }

    /**
     * 注入dubbo注册中心配置,基于zookeeper
     *
     * @return
     */
    @Bean
    public RegistryConfig registryConfig() {
        // 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setProtocol(protocol);
        registry.setAddress(registryAddress);
        return registry;
    }

    /**
     * 默认基于dubbo协议提供服务
     *
     * @return
     */
    @Bean
    public ProtocolConfig protocolConfig() {
        // 服务提供者协议配置
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName(protocolName);
        protocolConfig.setPort(protocolPort);
        protocolConfig.setThreads(200);
        System.out.println("默认protocolConfig：" + protocolConfig.hashCode());
        return protocolConfig;
    }

    /**
     * dubbo服务提供
     *
     * @param applicationConfig
     * @param registryConfig
     * @param protocolConfig
     * @return
     */
    @Bean(name="defaultProvider")
    public ProviderConfig providerConfig(ApplicationConfig applicationConfig, RegistryConfig registryConfig, ProtocolConfig protocolConfig) {
        System.out.println(delay);
        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.setTimeout(timeout);
        providerConfig.setRetries(retries);
        providerConfig.setDelay(delay);
        providerConfig.setApplication(applicationConfig);
        providerConfig.setRegistry(registryConfig);
        providerConfig.setProtocol(protocolConfig);
        return providerConfig;
    }
}
