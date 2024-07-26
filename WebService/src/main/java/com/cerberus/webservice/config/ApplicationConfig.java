package com.cerberus.webservice.config;

import com.cerberus.webservice.dto.StockPriceDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Sinks;

@Configuration
public class ApplicationConfig {

    @Bean
    public Sinks.Many<StockPriceDto> sink(){
        return Sinks.many().replay().limit(1);
    }
}
