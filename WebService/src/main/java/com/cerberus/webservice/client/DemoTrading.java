package com.cerberus.webservice.client;

import com.cerberus.webservice.dto.PortfolioItemDto;
import com.cerberus.webservice.dto.UserBalanceDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DemoTrading {

    Mono<UserBalanceDto> getBalance(Integer userId);

    Mono<UserBalanceDto> createBalance(Integer userId);

    Mono<UserBalanceDto> increaseBalance(Integer userId, Double sum);

    Flux<PortfolioItemDto> getUserHistory(Integer userId);
}
