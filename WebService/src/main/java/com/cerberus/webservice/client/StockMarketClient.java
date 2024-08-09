package com.cerberus.webservice.client;

import com.cerberus.webservice.dto.StockDto;
import com.cerberus.webservice.dto.StockPriceDto;
import com.cerberus.webservice.model.MarketStatus;
import com.cerberus.webservice.model.StockRecommendation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StockMarketClient {

    Flux<StockPriceDto> getStocksPricesWithPagination(Integer page, Integer size);

    Flux<StockRecommendation> getStockRecommendation(String ticker);

    Mono<StockPriceDto> getStockPrice(String ticker);

    Mono<StockDto> getStock(String ticker);

    Flux<StockDto> getAllStock();

    Mono<StockDto> createStock(Mono<StockDto> stockDto);

    Mono<StockDto> updateStock(Integer id, Mono<StockDto> stockDto);

    Mono<Void> deleteStock(Integer id);

    Mono<MarketStatus> getMarketStatus();
}
