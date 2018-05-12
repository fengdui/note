package com.fengdui.note.springaction;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author FD
 * @version v6.2.0
 * @date 2018/3/15
 */
@Configuration
public class FDInit {
    @Bean(
            initMethod = "initialize"
    )
    public FDService applicationStartedListener() {
        return new FDService();
    }
}
