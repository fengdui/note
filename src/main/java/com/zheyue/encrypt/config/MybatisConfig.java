package com.zheyue.encrypt.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author FD
 * @date 2016/11/29
 */
@Configuration
public class MybatisConfig {

    @Autowired
    DataSource dataSource;


    @Bean("sqlSessionFactory")
    public SqlSessionFactoryBean sqlSessionFactory() {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean;
    }

    /**
     * mybatis自动扫描配置
     * @return
     */
    @Bean("scannerConfigurer")
    public MapperScannerConfigurer scannerConfigurer() {
        MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
        scannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        scannerConfigurer.setBasePackage("com.zheyue.encrypt.dao");
        return scannerConfigurer;
    }


}
