package com.cerberus.webservice.client;

import com.cerberus.webservice.dto.PortfolioItemDto;
import com.cerberus.webservice.dto.TradeRequest;
import com.cerberus.webservice.dto.TradeResponse;
import com.cerberus.webservice.dto.UserBalanceDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DemoTrading {

    Mono<TradeResponse> trade(Mono<TradeRequest> tradeRequest);

    Mono<UserBalanceDto> getBalance(Integer userId);

    Mono<UserBalanceDto> createBalance(Integer userId);

    Mono<UserBalanceDto> increaseBalance(Integer userId, Double sum);

    Flux<PortfolioItemDto> getTradeHistory(Integer userId);
}
