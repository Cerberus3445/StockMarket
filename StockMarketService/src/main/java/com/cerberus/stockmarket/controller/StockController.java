package com.cerberus.stockmarket.controller;

import com.cerberus.stockmarket.client.StockClient;
import com.cerberus.stockmarket.dto.StockDto;
import com.cerberus.stockmarket.model.MarketStatus;
import com.cerberus.stockmarket.model.StockPrice;
import com.cerberus.stockmarket.model.StockRecommendation;
import com.cerberus.stockmarket.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stocks")
public class StockController {

    private final StockService stockService;

    private final StockClient stockClient;

    @GetMapping
    public Flux<StockDto> getAll(){
        return this.stockService.getAll();
    }

    @PostMapping
    public Mono<StockDto> create(@RequestBody Mono<StockDto> stockDtoMono){
        return this.stockService.create(stockDtoMono);
    }

    @GetMapping(value = "/price/page/{page}/size/{size}", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<StockPrice> getWithPagination(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        return this.stockClient.getPriceWithPagination(page, size);
    }

    @GetMapping("/{id}")
    public Mono<StockDto> getById(@PathVariable("id") Integer id){
        return this.stockService.getById(id);
    }

    @GetMapping("/ticker/{ticker}/recommendation")
    public Flux<StockRecommendation> getRecommendation(@PathVariable("ticker") String ticker){
        return this.stockClient.getRecommendation(ticker);
    }

    @GetMapping("/market-status/{market}")
    public Mono<MarketStatus> getMarketStatus(@PathVariable("market") String market){
        return this.stockClient.getMarketStatus(market);
    }

    @GetMapping("/ticker/{ticker}")
    public Mono<StockDto> getByTicker(@PathVariable("ticker") String ticker){
        return this.stockService.getByTicker(ticker);
    }

    @PatchMapping("/{id}")
    public Mono<StockDto> update(@PathVariable("id") Integer id, @RequestBody  Mono<StockDto> stockDtoMono) {
        return this.stockService.update(id, stockDtoMono);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable("id") Integer id){
        return this.stockService.delete(id);
    }

    @GetMapping("/{ticker}/price")
    public Mono<StockPrice> getPrice(@PathVariable("ticker") String ticker){
        return this.stockClient.getPrice(ticker);
    }
}
