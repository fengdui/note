package com.zheyue.encrypt.config;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author FD
 * @date 2016/11/29
 */
@Configuration
//@MapperScan("com.zheyue.encrypt.dao")
public class MybatisConfig {

//    @Autowired
//    DataSource dataSource;

    /**
     * sqlSessionFactory在MybatisAutoConfiguration已经自动创建
     * @return
     */
//    @Bean("sqlSessionFactory")
//    public SqlSessionFactoryBean sqlSessionFactory() {
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(dataSource);
//        return sqlSessionFactoryBean;
//    }

    /**
     * 使用@MapperScan("com.zheyue.encrypt.dao")注解代替
     * mybatis自动扫描配置
     * @return
     */
    /*@Bean("scannerConfigurer")
    public MapperScannerConfigurer scannerConfigurer() {
        MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
        scannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        scannerConfigurer.setBasePackage("com.zheyue.encrypt.dao");
        return scannerConfigurer;
    }*/


}
