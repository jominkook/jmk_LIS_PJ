package com.yk.logistic.config.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerConfig {
    @Bean
    public Logger itemViewControllerLogger() {
        return LoggerFactory.getLogger("ItemViewControllerLogger");
    }
}
