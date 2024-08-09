package com.cerberus.stockmarket.client.impl;

import com.cerberus.stockmarket.client.StockClient;
import com.cerberus.stockmarket.model.MarketStatus;
import com.cerberus.stockmarket.model.StockPrice;
import com.cerberus.stockmarket.model.StockRecommendation;
import com.cerberus.stockmarket.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

@Component
@Slf4j
@RequiredArgsConstructor
public class StockClientImpl implements StockClient {

    @Value("${finnhub.token}")
    private String token;

    private final Sinks.Many<StockPrice> sink;

    private final StockService stockService;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://finnhub.io/api/v1")
            .build();

    @Override
    public Mono<StockPrice> getPrice(String ticker) {
        log.info("getPrice {}", ticker);
        var path = "/quote";
        var query = "symbol={ticker}&token={token}";
        var map = Map.of(
                "ticker", ticker,
                "token", token
        );
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path(path).query(query).build(map))
                .retrieve()
                .bodyToMono(StockPrice.class)
                .doOnNext(stockPriceDto -> stockPriceDto.setTicker(ticker))
                .map(forTestStock())
                .doOnNext(this.sink::tryEmitNext);
    }

    @Override
    public Flux<StockPrice> getPriceWithPagination(Integer page, Integer size) {
        return this.stockService.getWithPagination(page, size)
                .flatMap(stockDto -> getPrice(stockDto.ticker()))
                .as(stockPriceDtoFlux -> sink.asFlux());
    }

    @Override
    public Flux<StockRecommendation> getRecommendation(String ticker) {
        log.info("getRecommendation {}", ticker);
        var path = "/stock/recommendation";
        var query = "symbol={ticker}&token={token}";
        var map = Map.of(
                "ticker", ticker,
                "token", token
        );
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path(path).query(query).build(map))
                .retrieve()
                .bodyToFlux(StockRecommendation.class)
                .doOnNext(stockRecommendation -> stockRecommendation.setTicker(ticker));
    }

    @Override
    public Mono<MarketStatus> getMarketStatus(String market) {
        log.info("getMarketStatus {}", market);
        var path = "/stock/market-status";
        var query = "exchange={exchange}&token={token}";
        var map = Map.of(
          "exchange" , market,
          "token", token
        );
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path(path).query(query).build(map))
                .retrieve()
                .bodyToMono(MarketStatus.class);
    }

    private Function<StockPrice, StockPrice> forTestStock(){ //для акции TEST
        return stockPrice -> {
            Random random = new Random();
            if (stockPrice.getTicker().equalsIgnoreCase("TEST")){
                stockPrice.setC(random.nextDouble(100.0));
                return stockPrice;
            } else {
                return stockPrice;
            }
        };
    }
}
