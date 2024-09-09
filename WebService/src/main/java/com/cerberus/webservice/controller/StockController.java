package com.cerberus.webservice.controller;

import com.cerberus.webservice.client.StockMarketClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
@RequestMapping("/stockmarket")
public class StockController {

    private final StockMarketClient stockMarketClient;

    @Value("${token}")
    private String token;

    @GetMapping("/main")
    public Mono<String> mainPage(Model model){
        return this.stockMarketClient.getStocksWithPagination(1, 10, token)
                .collectList()
                .doOnNext(stockPrices -> model.addAttribute("stockPricesList", stockPrices))
                .thenReturn("stock/main");
    }

    @GetMapping("/{ticker}")
    public String getStock(Model model, @PathVariable("ticker") String ticker){
        if(ticker.equalsIgnoreCase("TEST")){ //для акции TEST нет рекомендаций
            model.addAttribute("stockPrice", this.stockMarketClient.getStockPrice(ticker, token));
        } else {
            model.addAttribute("stockRecommendationList", this.stockMarketClient.getStockRecommendation(ticker, token));
            model.addAttribute("stockPrice", this.stockMarketClient.getStockPrice(ticker, token));
        }
        return "stock/aboutStock";
    }
}
