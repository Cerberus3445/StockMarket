package com.cerberus.demotradingweb.client.impl;

import com.cerberus.demotradingweb.client.DemoTrading;
import com.cerberus.demotradingweb.dto.PortfolioItemDto;
import com.cerberus.demotradingweb.dto.TradeRequest;
import com.cerberus.demotradingweb.dto.TradeResponse;
import com.cerberus.demotradingweb.dto.UserBalanceDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class DemoTradingImpl implements DemoTrading {

    private final RestClient restClient = RestClient.builder()
            .baseUrl("http://demotrading:5050/api/v1/trade")
            .build();

    @Override
    public TradeResponse trade(TradeRequest tradeRequest, String token) {
        log.info("update {}", tradeRequest);
        return this.restClient.post()
                .header("Auth-Token", token)
                .body(tradeRequest)
                .retrieve()
                .body(TradeResponse.class);
    }

    @Override
    public UserBalanceDto getBalance(Integer userId, String token) {
        log.info("getBalance {}", userId);
        return this.restClient.get()
                .uri("/user/{userId}", userId)
                .header("Auth-Token", token)
                .retrieve()
                .body(UserBalanceDto.class);
    }

    @Override
    public UserBalanceDto createBalance(Integer userId, String token) {
        log.info("createBalance {}", userId);
        return this.restClient.post()
                .uri("/user/{userId}", userId)
                .header("Auth-Token", token)
                .retrieve()
                .body(UserBalanceDto.class);
    }

    @Override
    public UserBalanceDto increaseBalance(Integer userId, Double sum, String token) {
        log.info("increaseBalance {} {}", userId, sum);
        return this.restClient.post()
                .uri("/user/{userId}/increase?sum={sum}", userId, sum)
                .header("Auth-Token", token)
                .retrieve()
                .body(UserBalanceDto.class);
    }

    @Override
    public List<PortfolioItemDto> getTradeHistory(Integer userId, String token) {
        log.info("getTradeHistory {}", userId);
        return Arrays.stream(Objects.requireNonNull(this.restClient.get()
                        .uri("/user/{userId}/history", userId)
                        .header("Auth-Token", token)
                        .retrieve()
                        .body(PortfolioItemDto[].class)))
                .toList();
    }
}
