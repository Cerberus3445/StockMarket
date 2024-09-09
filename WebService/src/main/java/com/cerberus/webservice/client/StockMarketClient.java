package com.cerberus.webservice.client;

import com.cerberus.webservice.dto.StockDto;
import com.cerberus.webservice.model.StockPrice;
import com.cerberus.webservice.model.MarketStatus;
import com.cerberus.webservice.model.StockRecommendation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StockMarketClient {

    Flux<StockPrice> getStreamStocksPrices(String token);

    Flux<StockRecommendation> getStockRecommendation(String ticker, String token);

    Mono<StockDto> getStock(String ticker, String token);

    Flux<StockDto> getAllStock();

    Mono<StockDto> createStock(Mono<StockDto> stockDto);

    Mono<StockDto> updateStock(Integer id, Mono<StockDto> stockDto);

    Mono<Void> deleteStock(Integer id);

    Flux<StockPrice> getStocksWithPagination(Integer page, Integer size, String token);

    Mono<StockPrice> getStockPrice(String ticker, String token);

    Mono<MarketStatus> getMarketStatus();
}
