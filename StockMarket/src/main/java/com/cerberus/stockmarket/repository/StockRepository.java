package com.cerberus.stockmarket.repository;

import com.cerberus.stockmarket.model.Stock;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface StockRepository extends ReactiveCrudRepository<Stock, Integer> {

    Mono<Stock> findByTicker(String ticker);

    Flux<Stock> findBy(Pageable pageable);

    @Modifying
    @Query("delete from stock_market.Stocks where id=:id")
    Mono<Boolean> deleteWithId(Integer id);
}
