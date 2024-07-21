package com.cerberus.stockmarket.controller;

import com.cerberus.stockmarket.client.StockClient;
import com.cerberus.stockmarket.dto.StockDto;
import com.cerberus.stockmarket.dto.StockPriceDto;
import com.cerberus.stockmarket.service.StockService;
import com.cerberus.stockmarket.validator.RequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/stocks")
public class StockController {

    private final StockService stockService;

    private final StockClient stockClient;

    @GetMapping
    public Flux<StockDto> getAll(){
        return this.stockService.getAll();
    }

    @PostMapping
    public Mono<StockDto> create(@RequestBody Mono<StockDto> stockDtoMono){
        return stockDtoMono.transform(RequestValidator.validate())
                .as(this.stockService::create);
    }

    @GetMapping("/{id}")
    public Mono<StockDto> getById(@PathVariable("id") Integer id){
        return this.stockService.getById(id);
    }

    @GetMapping("/ticker")
    public Mono<StockDto> getByTicker(@RequestParam("ticker") String ticker, @RequestHeader("Accept-Language") Locale locale){
        return this.stockService.getByTicker(ticker);
    }

    @PatchMapping("/{id}")
    public Mono<StockDto> update(@PathVariable("id") Integer id, @RequestBody  Mono<StockDto> stockDtoMono) {
        return stockDtoMono.transform(RequestValidator.validate())
                .as(i -> this.stockService.update(id, i));
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable("id") Integer id){
        return this.stockService.delete(id)
                .filter(i -> i == true)
                .then();
    }

    @GetMapping("/{ticker}/price")
    public Mono<StockPriceDto> getPrice(@PathVariable("ticker") String ticker){
        return this.stockClient.getPrice(ticker);
    }
}
