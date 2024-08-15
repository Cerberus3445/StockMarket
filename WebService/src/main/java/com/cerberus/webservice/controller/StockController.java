package com.cerberus.webservice.controller;

import com.cerberus.webservice.client.StockMarketClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/stockmarket")
public class StockController {

    private final StockMarketClient stockMarketClient;

    @GetMapping("/main")
    public String mainPage(Model model){
        model.addAttribute("stocks1Pillar", this.stockMarketClient.getStocksWithPagination(1, 5));
        return "stock/main";
    }

    @GetMapping("/{ticker}")
    public String getStock(Model model, @PathVariable("ticker") String ticker){
        model.addAttribute("stockRecommendationList", this.stockMarketClient.getStockRecommendation(ticker));
        model.addAttribute("stockPrice", this.stockMarketClient.getStockPrice(ticker));
        return "stock/aboutStock";
    }
}
