package com.zheyue.encrypt.config;

import com.zheyue.encrypt.concurrency.DownloadExecutor;
import com.zheyue.encrypt.server.FileServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author FD
 * @date 2016/12/29
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
