package com.cerberus.webservice.client.impl;

import com.cerberus.webservice.client.StockClient;
import com.cerberus.webservice.dto.StockPriceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Component
@RequiredArgsConstructor
public class StockClientImpl implements StockClient {

    private final Sinks.Many<StockPriceDto> sink;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:6060/api/v1/stocks")
            .build();

    public Flux<StockPriceDto> getStocksPricesWithPagination(Integer page, Integer size){
        return this.webClient.get()
                .uri("/price/page/{page}/size/{size}", page, size)
                .accept(MediaType.APPLICATION_NDJSON)
                .retrieve()
                .bodyToFlux(StockPriceDto.class)
                .doOnNext(this.sink::tryEmitNext);
    }

}
