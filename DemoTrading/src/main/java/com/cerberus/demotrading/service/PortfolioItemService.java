package com.cerberus.demotrading.service;

import com.cerberus.demotrading.dto.PortfolioItemDto;
import com.cerberus.demotrading.model.TradeResponse;
import com.cerberus.demotrading.model.TradeRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PortfolioItemService {

    Mono<TradeResponse> trade(TradeRequest tradeRequest);

    Flux<PortfolioItemDto> getTradeHistory(Integer userId);
}
