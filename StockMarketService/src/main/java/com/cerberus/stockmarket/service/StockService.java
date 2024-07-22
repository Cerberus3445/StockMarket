package com.cerberus.stockmarket.service;

import com.cerberus.stockmarket.dto.StockDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StockService {

    Mono<StockDto> getByTicker(String ticker);

    Mono<StockDto> getById(Integer id);

    Flux<StockDto> getAll();

    Mono<StockDto> create(Mono<StockDto> dtoMono);

    Flux<StockDto> getByPage(Integer page, Integer size);

    Mono<StockDto> update(Integer id, Mono<StockDto> dtoMono);

    Mono<Boolean> delete(Integer id);
}
