package com.cerberus.demotradingweb.client.impl;

import com.cerberus.demotradingweb.client.StockMarketClient;
import com.cerberus.demotradingweb.model.StockPrice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@Slf4j
@RequiredArgsConstructor
public class StockMarketClientImpl implements StockMarketClient {

    private final RestClient restClient = RestClient.builder()
            .baseUrl("http://stock:6060/api/v1/stocks")
            .build();

    @Override
    public StockPrice getPrice(String ticker, String token) {
        log.info("getPrice {}", ticker);
        return this.restClient.get()
                .uri("/{ticker}/price", ticker)
                .header("Auth-Token", token)
                .retrieve()
                .body(StockPrice.class);
    }
}
