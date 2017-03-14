package com.fengdui.extjs.config;

import org.mybatis.spring.annotation.MapperScan;

/**
 * sqlSessionFactory在MybatisAutoConfiguration已经自动创建
 * MybatisAutoConfiguration使用@EnableConfigurationProperties(MybatisProperties.class)
 * 所以直接在配置文件加上mybatis前缀的配置
 * @author FD
 * @date 2017/3/13
 */
@MapperScan("com.fengdui.extjs.mapper")
public class MybatisConfig {

}
