package com.cerberus.webservice.controller;

import com.cerberus.webservice.client.StockClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.spring6.context.webflux.IReactiveDataDriverContextVariable;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;

@Controller
@RequestMapping("/web/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockClient stockClient;

    @GetMapping()
    public String getPrices(final Model model){
        IReactiveDataDriverContextVariable iReactiveDataDriverContextVariable =
                new ReactiveDataDriverContextVariable(stockClient.getStocksPricesWithPagination(1, 10), 1);
        model.addAttribute("stockList", iReactiveDataDriverContextVariable);
        return "stock/list";
    }
}
