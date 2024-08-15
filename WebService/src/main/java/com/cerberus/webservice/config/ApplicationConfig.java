package com.cerberus.webservice.config;

import com.cerberus.webservice.model.StockPrice;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Sinks;

@Configuration
public class ApplicationConfig {

    @Bean
    public Sinks.Many<StockPrice> sink(){
        return Sinks.many().replay().limit(1);
    }
}
