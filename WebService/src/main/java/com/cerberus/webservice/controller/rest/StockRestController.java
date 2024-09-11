package com.cerberus.webservice.controller.rest;

import com.cerberus.webservice.client.StockMarketClient;
import com.cerberus.webservice.model.StockPrice;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/*
Этот контроллер нужен для js
 */

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockRestController {

    private final StockMarketClient stockMarketClient;

    @Value("${token}")
    private String token;


    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<StockPrice> getPricesForJs(){
        return this.stockMarketClient.getStreamStocksPrices(token);
    }

    @Scheduled(fixedDelay = 6500)
    private void updatePrices(){
        this.stockMarketClient.getStocksWithPagination(1, 10, token)
                .subscribe();
    }
}
