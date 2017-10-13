package com.fengdui.test.extjs.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * sqlSessionFactory在MybatisAutoConfiguration已经自动创建
 * MybatisAutoConfiguration使用@EnableConfigurationProperties(MybatisProperties.class)
 * 所以直接在配置文件加上mybatis前缀的配置
 * @author FD
 * @date 2017/3/13
 */
@Configuration
@MapperScan(basePackages = {"com.fengdui.extjs.mapper"})
public class MybatisConfig {

}
