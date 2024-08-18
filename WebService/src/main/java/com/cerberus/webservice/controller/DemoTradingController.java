package com.cerberus.webservice.controller;

import com.cerberus.webservice.client.DemoTrading;
import com.cerberus.webservice.client.StockMarketClient;
import com.cerberus.webservice.dto.TradeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
@RequestMapping("/stockmarket/{userId}")
public class DemoTradingController {

    private final DemoTrading demoTrading;

    private final StockMarketClient stockMarketClient;

    @GetMapping("/trading")
    public Mono<String> tradingPage(@PathVariable("userId") Integer userId, Model model){
//        model.addAttribute("balance", this.demoTrading.getBalance(userId));
//        model.addAttribute("tradeHistory", this.demoTrading.getTradeHistory(userId));
//        return "stock/trading"
        return this.demoTrading.getTradeHistory(userId)
                .collectList()
                .doOnNext(portfolioItemDtoList -> model.addAttribute("tradeHistory", portfolioItemDtoList))
                .flatMap(portfolioItemDto -> this.demoTrading.getBalance(userId))
                .doOnNext(userBalanceDto -> model.addAttribute("userBalance", userBalanceDto))
                .doOnNext(i -> model.addAttribute("TradeRequest", new TradeRequest()))
                .then()
                .thenReturn("stock/trading");
    }

    @PostMapping("/createBalance")
    public Mono<String> createBalance(@PathVariable("userId") Integer userId){
        return this.demoTrading.createBalance(userId).
                thenReturn("stock/trading");
    }

    @PostMapping("/increaseBalance")
    public Mono<String> increaseBalance(@PathVariable("userId") Integer userId, @ModelAttribute("sum") Double sum){
        return this.demoTrading.increaseBalance(userId, sum)
                .thenReturn("stock/trading");
    }

    @PostMapping("/trade")
    public Mono<String> trade(@PathVariable("userId") Integer userId, @ModelAttribute("tradeRequest") TradeRequest tradeRequest){
        return this.stockMarketClient.getStockPrice(tradeRequest.getTicker())
                .map(stockPrice -> new TradeRequest(tradeRequest.getTicker(), tradeRequest.getTradeAction(),
                        tradeRequest.getQuantity(), stockPrice.getC(), userId))
                .thenReturn("stock/trading");
    }
}
