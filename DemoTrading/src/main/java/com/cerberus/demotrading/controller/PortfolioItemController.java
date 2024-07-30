package com.cerberus.demotrading.controller;

import com.cerberus.demotrading.dto.PortfolioItemDto;
import com.cerberus.demotrading.model.TradeRequest;
import com.cerberus.demotrading.model.TradeResponse;
import com.cerberus.demotrading.service.PortfolioItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/trade")
@RequiredArgsConstructor
public class PortfolioItemController {

    private final PortfolioItemService portfolioItemService;

    @PostMapping()
    public Mono<TradeResponse> trade(@RequestBody Mono<TradeRequest> tradeRequest){
        return tradeRequest.flatMap(this.portfolioItemService::trade);
    }

    @GetMapping("/user/{id}/history")
    public Flux<PortfolioItemDto> getTradeHistory(@PathVariable("id") Integer userId){
        return this.portfolioItemService.getTradeHistory(userId);
    }
}
