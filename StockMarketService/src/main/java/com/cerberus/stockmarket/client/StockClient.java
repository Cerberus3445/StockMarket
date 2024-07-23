package com.cerberus.stockmarket.client;

import com.cerberus.stockmarket.dto.StockPriceDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StockClient {

    Mono<StockPriceDto> getPrice(String ticker);

    Flux<StockPriceDto> getPriceWithPagination(Integer page, Integer size);
}
