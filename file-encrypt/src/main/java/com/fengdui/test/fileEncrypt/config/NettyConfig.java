package com.fengdui.test.fileEncrypt.config;

import com.fengdui.test.fileEncrypt.concurrency.DownloadExecutor;
import com.fengdui.test.fileEncrypt.server.FileServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author FD
 * @date 2016/12/29
 * netty相关的配置文件
 */
@Configuration
public class NettyConfig {

    @Bean
    public FileServer fileServer() {
        return new FileServer();
    }

    @Bean
    public DownloadExecutor downloadExecutor() {
        return new DownloadExecutor();
    }
}
