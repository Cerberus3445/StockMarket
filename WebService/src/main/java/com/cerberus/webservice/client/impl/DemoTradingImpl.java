package com.cerberus.webservice.client.impl;

import com.cerberus.webservice.client.DemoTrading;
import com.cerberus.webservice.dto.PortfolioItemDto;
import com.cerberus.webservice.dto.TradeRequest;
import com.cerberus.webservice.dto.TradeResponse;
import com.cerberus.webservice.dto.UserBalanceDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class DemoTradingImpl implements DemoTrading {

    private final WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:5050/api/v1/trade")
            .build();

    @Override
    public Mono<TradeResponse> trade(Mono<TradeRequest> tradeRequest) {
        return this.webClient.post()
                .body(tradeRequest, TradeRequest.class)
                .retrieve()
                .bodyToMono(TradeResponse.class);
    }

    @Override
    public Mono<UserBalanceDto> getBalance(Integer userId) {
        return this.webClient.get()
                .uri("/user/{userId}", userId)
                .retrieve()
                .bodyToMono(UserBalanceDto.class);
    }

    @Override
    public Mono<UserBalanceDto> createBalance(Integer userId) {
        return this.webClient.post()
                .uri("/user/{userId}", userId)
                .retrieve()
                .bodyToMono(UserBalanceDto.class);
    }

    @Override
    public Mono<UserBalanceDto> increaseBalance(Integer userId, Double sum) {
        return this.webClient.post()
                .uri("/user/{userId}/increase?sum={sum}", userId, sum)
                .retrieve()
                .bodyToMono(UserBalanceDto.class);
    }

    @Override
    public Flux<PortfolioItemDto> getTradeHistory(Integer userId) {
        return this.webClient.get()
                .uri("/user/{userId}/history", userId)
                .retrieve()
                .bodyToFlux(PortfolioItemDto.class);
    }
}
