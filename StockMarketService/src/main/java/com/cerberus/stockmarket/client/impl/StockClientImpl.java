package com.cerberus.stockmarket.client.impl;

import com.cerberus.stockmarket.client.StockClient;
import com.cerberus.stockmarket.dto.StockPriceDto;
import com.cerberus.stockmarket.service.StockService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class StockClientImpl implements StockClient {

    @Value("${finnhub.token}")
    private String token;

    private final Logger logger = LoggerFactory.getLogger(StockClientImpl.class);

    private final Sinks.Many<StockPriceDto> sink;

    private final StockService stockService;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://finnhub.io/api/v1")
            .build();

    @Override
    public Mono<StockPriceDto> getPrice(String ticker) {
        logger.info("getPrice {}", ticker);
        var path = "/quote";
        var query = "symbol={ticker}&token={token}";
        var map = Map.of(
                "ticker", ticker,
                "token", token
        );
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path(path).query(query).build(map))
                .retrieve()
                .bodyToMono(StockPriceDto.class)
                .doOnNext(stockPriceDto -> stockPriceDto.setTicker(ticker))
                .doOnNext(this.sink::tryEmitNext);
    }

    @Override
    public Flux<StockPriceDto> getPriceWithPagination(Integer page, Integer size) {
        return this.stockService.getWithPagination(page, size)
                .flatMap(stockDto -> getPrice(stockDto.ticker()))
                .as(stockPriceDtoFlux -> sink.asFlux());
    }

}
