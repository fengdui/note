package com.zheyue.encrypt.config;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author FD
 * @date 2016/11/28
 */
//@Configuration
public class DataSourceConfig {

    @Autowired
    private DataSourceProperties properties;

//    @Bean("dataSource")
//    public DataSource dataSource() throws Exception {
//        Properties props = new Properties();
//        props.put("driverClassName", properties.getDriverClassName());
//        props.put("url", properties.getUrl());
//        props.put("username", properties.getUsername());
//        props.put("password", properties.getPassword());
//        return DruidDataSourceFactory.createDataSource(props);
//    }

//    @Bean
//    public FilterRegistrationBean druidWebStatFilter() {
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        registrationBean.setFilter(new WebStatFilter());
//        registrationBean.addUrlPatterns("/*");
//        return registrationBean;
//    }
//
//    @Bean
//    ServletRegistrationBean druidStatViewServlet() {
//        ServletRegistrationBean registrationBean = new ServletRegistrationBean();
//        registrationBean.setServlet(new StatViewServlet());
//        registrationBean.addUrlMappings("/druid/*");
//        return registrationBean;
//    }
}
