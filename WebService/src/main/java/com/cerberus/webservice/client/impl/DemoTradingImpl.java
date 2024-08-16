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
        tradeRequest.doOnNext(tradeRequest1 -> log.info("trade {}", tradeRequest1));
        return this.webClient.post()
                .body(tradeRequest, TradeRequest.class)
                .retrieve()
                .bodyToMono(TradeResponse.class);
    }

    @Override
    public Mono<UserBalanceDto> getBalance(Integer userId) {
        log.info("getBalance {}", userId);
        return this.webClient.get()
                .uri("/user/{userId}", userId)
                .retrieve()
                .bodyToMono(UserBalanceDto.class);
    }

    @Override
    public Mono<UserBalanceDto> createBalance(Integer userId) {
        log.info("createBalance {}", userId);
        return this.webClient.post()
                .uri("/user/{userId}", userId)
                .retrieve()
                .bodyToMono(UserBalanceDto.class);
    }

    @Override
    public Mono<UserBalanceDto> increaseBalance(Integer userId, Double sum) {
        log.info("increaseBalance {} {}", userId, sum);
        return this.webClient.post()
                .uri("/user/{userId}/increase?sum={sum}", userId, sum)
                .retrieve()
                .bodyToMono(UserBalanceDto.class);
    }

    @Override
    public Flux<PortfolioItemDto> getTradeHistory(Integer userId) {
        log.info("getTradeHistory {}", userId);
        return this.webClient.get()
                .uri("/user/{userId}/history", userId)
                .retrieve()
                .bodyToFlux(PortfolioItemDto.class);
    }
}
