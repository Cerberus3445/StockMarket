package com.cerberus.webservice.client;

import com.cerberus.webservice.dto.StockDto;
import com.cerberus.webservice.model.StockPrice;
import com.cerberus.webservice.model.MarketStatus;
import com.cerberus.webservice.model.StockRecommendation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StockMarketClient {

    Flux<StockPrice> getStreamStocksPrices();

    Flux<StockRecommendation> getStockRecommendation(String ticker);

    Mono<StockDto> getStock(String ticker);

    Flux<StockDto> getAllStock();

    Mono<StockDto> createStock(Mono<StockDto> stockDto);

    Mono<StockDto> updateStock(Integer id, Mono<StockDto> stockDto);

    Mono<Void> deleteStock(Integer id);

    Flux<StockPrice> getStocksWithPagination(Integer page, Integer size);

    Mono<StockPrice> getStockPrice(String ticker);

    Mono<MarketStatus> getMarketStatus();
}
