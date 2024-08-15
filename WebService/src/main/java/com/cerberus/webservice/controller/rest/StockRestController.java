package com.cerberus.webservice.controller.rest;

import com.cerberus.webservice.client.StockMarketClient;
import com.cerberus.webservice.dto.StockPriceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<StockPriceDto> mainPage(){
        return this.stockMarketClient.getStreamStocksPrices();
    }
}
