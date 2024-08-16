package com.cerberus.webservice.controller.rest;

import com.cerberus.webservice.client.DemoTrading;
import com.cerberus.webservice.client.StockMarketClient;
import com.cerberus.webservice.dto.UserBalanceDto;
import com.cerberus.webservice.model.StockPrice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/*
Этот контроллер нужен для js
 */

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockRestController {

    private final StockMarketClient stockMarketClient;

    private final DemoTrading demoTrading;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<StockPrice> getPricesForJs(){
        return this.stockMarketClient.getStreamStocksPrices();
    }

    @GetMapping("/userbalance/{id}")
    public Mono<UserBalanceDto> mainPage(@PathVariable("id") Integer id){
        return this.demoTrading.getBalance(id);
    }
}
