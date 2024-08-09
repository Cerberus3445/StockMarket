package com.cerberus.webservice.controller;

import com.cerberus.webservice.client.StockMarketClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockMarketClient stockMarketClient;

    @GetMapping()
    public String mainPage(Model model){
        return "stock/main";
    }

    @GetMapping("/ticker")
    public String getPrices(Model model){
        return "stock/list";
    }
}
