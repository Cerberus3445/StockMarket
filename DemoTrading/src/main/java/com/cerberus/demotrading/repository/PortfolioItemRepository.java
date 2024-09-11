package com.cerberus.demotrading.repository;

import com.cerberus.demotrading.model.PortfolioItem;
import com.cerberus.demotrading.model.TradeAction;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface PortfolioItemRepository extends ReactiveCrudRepository<PortfolioItem, Integer> {

    Flux<PortfolioItem> findByUserId(Integer userId);

    /*
    если искать без tradeAction, то будет находить один и тот же portfolioItem, а другому portfolioItem, которому
    нужно поменять с buy на sell, ничего не будет, просто будет увеличиваться баланс пользователя
     */
    @Query("select * from portfolio_items where user_id = :userId and trade_action = :tradeAction and ticker = :ticker and quantity >= :quantity limit 1;")
    Mono<PortfolioItem> findPortfolioItem(Integer userId, String ticker, TradeAction tradeAction, Integer quantity);

}
