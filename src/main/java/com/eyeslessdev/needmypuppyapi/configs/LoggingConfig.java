package com.eyeslessdev.needmypuppyapi.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class LoggingConfig {

    @Bean
    public Logger logger(){
        return LoggerFactory.getLogger("myCustomLogging");
    }
}
