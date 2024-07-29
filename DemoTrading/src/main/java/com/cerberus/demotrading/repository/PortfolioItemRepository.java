package com.cerberus.demotrading.repository;

import com.cerberus.demotrading.model.PortfolioItem;
import com.cerberus.demotrading.model.TradeAction;
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
    Mono<PortfolioItem> findFirstByUserIdAndTickerAndQuantityAndTradeAction(Integer userId, String ticker, Integer quantity, TradeAction tradeAction);


}
