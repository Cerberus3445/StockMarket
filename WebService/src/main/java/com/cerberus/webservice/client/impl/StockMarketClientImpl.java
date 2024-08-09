package com.cerberus.webservice.client.impl;

import com.cerberus.webservice.client.StockMarketClient;
import com.cerberus.webservice.dto.StockDto;
import com.cerberus.webservice.dto.StockPriceDto;
import com.cerberus.webservice.model.MarketStatus;
import com.cerberus.webservice.model.StockRecommendation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Component
@RequiredArgsConstructor
public class StockMarketClientImpl implements StockMarketClient {

    private final Sinks.Many<StockPriceDto> sink;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:6060/api/v1/stocks")
            .build();

    @Override
    public Flux<StockPriceDto> getStocksPricesWithPagination(Integer page, Integer size){
        return this.webClient.get()
                .uri("/price/page/{page}/size/{size}", page, size)
                .accept(MediaType.APPLICATION_NDJSON)
                .retrieve()
                .bodyToFlux(StockPriceDto.class)
                .doOnNext(this.sink::tryEmitNext);
    }

    @Override
    public Flux<StockRecommendation> getStockRecommendation(String ticker) {
        return this.webClient.get()
                .uri("/ticker/{ticker}/recommendation", ticker)
                .retrieve()
                .bodyToFlux(StockRecommendation.class);
    }

    @Override
    public Mono<StockDto> getStock(String ticker) {
        return this.webClient.get()
                .uri("/ticker/{ticker}", ticker)
                .retrieve()
                .bodyToMono(StockDto.class);
    }

    @Override
    public Flux<StockDto> getAllStock() {
        return this.webClient.get()
                .retrieve()
                .bodyToFlux(StockDto.class);
    }

    @Override
    public Mono<StockDto> createStock(Mono<StockDto> stockDto) {
        return this.webClient.post()
                .body(stockDto, StockDto.class)
                .retrieve()
                .bodyToMono(StockDto.class);
    }

    @Override
    public Mono<StockDto> updateStock(Integer id, Mono<StockDto> stockDto) {
        return this.webClient.patch()
                .uri("/{id}", id)
                .body(stockDto, StockDto.class)
                .retrieve()
                .bodyToMono(StockDto.class);
    }

    @Override
    public Mono<Void> deleteStock(Integer id) {
        return this.webClient.delete()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
    }

    @Override
    public Mono<MarketStatus> getMarketStatus() {
        return this.webClient.get()
                .uri("/market-status/US")
                .retrieve()
                .bodyToMono(MarketStatus.class);
    }

}
