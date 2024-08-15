package com.cerberus.stockmarket.client;

import com.cerberus.stockmarket.dto.StockDto;
import com.cerberus.stockmarket.model.MarketStatus;
import com.cerberus.stockmarket.model.StockPrice;
import com.cerberus.stockmarket.model.StockRecommendation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StockClient {

    Mono<StockPrice> getPrice(String ticker);

    Flux<StockPrice> getStreamPrice();

    Flux<StockRecommendation> getRecommendation(String ticker);

    Mono<MarketStatus> getMarketStatus(String market);

    Flux<StockPrice> getWithPagination(Integer page, Integer size);
}
