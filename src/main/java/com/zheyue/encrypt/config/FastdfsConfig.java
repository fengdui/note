package com.zheyue.encrypt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author FD
 * @date 2016/12/28
 */

//@Configuration
public class FastdfsConfig {

    @Value("${fs.connect_timeout}")
    private String connectTimeOut;


    @Bean
    public void fileSystemPool() {

    }
}
