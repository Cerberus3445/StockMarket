package com.cerberus.webservice.controller;

import com.cerberus.webservice.client.DemoTrading;
import com.cerberus.webservice.client.StockMarketClient;
import com.cerberus.webservice.dto.TradeRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/stockmarket/{userId}")
public class DemoTradingController {

    private final DemoTrading demoTrading;

    private final StockMarketClient stockMarketClient;

    @GetMapping("/trading")
    public Mono<String> tradingPage(@PathVariable("userId") Integer userId, Model model){
        return this.demoTrading.getTradeHistory(userId)
                .collectList()
                .doOnNext(portfolioItemDtoList -> model.addAttribute("tradeHistory", portfolioItemDtoList))
                .flatMap(portfolioItemDto -> this.demoTrading.getBalance(userId))
                .doOnNext(userBalanceDto -> model.addAttribute("userBalance", userBalanceDto))
                .doOnNext(i -> model.addAttribute("tradeRequest", new TradeRequest()))
                .then()
                .thenReturn("stock/trading");
    }

    @PostMapping("/createBalance")
    public Mono<String> createBalance(@PathVariable("userId") Integer userId){
        return this.demoTrading.createBalance(userId).
                thenReturn("redirect:http://localhost:9095/stockmarket/%d/trade".formatted(userId));
    }

    @PostMapping("/increaseBalance")
    public Mono<String> increaseBalance(@PathVariable("userId") Integer userId, @ModelAttribute("sum") Double sum){
        return this.demoTrading.increaseBalance(userId, sum)
                .thenReturn("redirect:http://localhost:9095/stockmarket/%d/trade".formatted(userId));
    }

    @PostMapping("/trade")
    public Mono<String> trade(@PathVariable("userId") Integer userId, @ModelAttribute("tradeRequest") TradeRequest tradeRequest){
        return this.stockMarketClient.getStockPrice(tradeRequest.getTicker())
                .map(stockPrice -> new TradeRequest(tradeRequest.getTicker(), tradeRequest.getTradeAction(),
                        tradeRequest.getQuantity(), stockPrice.getC(), userId))
                .thenReturn("redirect:http://localhost:9095/stockmarket/%d/trade".formatted(userId));
    }
}
