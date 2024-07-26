package com.cerberus.stockmarket.config;

import com.cerberus.stockmarket.model.StockPrice;
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
