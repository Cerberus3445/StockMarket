package com.cerberus.demotradingweb.controller;

import com.cerberus.demotradingweb.client.DemoTrading;
import com.cerberus.demotradingweb.client.StockMarketClient;
import com.cerberus.demotradingweb.dto.PortfolioItemDto;
import com.cerberus.demotradingweb.dto.TradeRequest;
import com.cerberus.demotradingweb.dto.UserBalanceDto;
import com.cerberus.demotradingweb.model.StockPrice;
import com.cerberus.demotradingweb.security.PersonDetails;
import com.cerberus.demotradingweb.service.AuthTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/stockmarket/trading")
public class DemoTradingController {

    private final DemoTrading demoTrading;

    private final StockMarketClient stockMarketClient;

    public final AuthTokenService authTokenService;

    @GetMapping
    public String tradingPage(Model model) {
        String token = this.authTokenService.get(getUserId()).getValue();
        List<PortfolioItemDto> portfolioItemDto = this.demoTrading.getTradeHistory(getUserId(), token);
        UserBalanceDto userBalanceDto = this.demoTrading.getBalance(getUserId(), token);

        model.addAttribute("tradeHistory", portfolioItemDto);
        if(userBalanceDto != null){
            model.addAttribute("userBalance", userBalanceDto);
        } else {
            model.addAttribute("noUserBalance", 1);
        }
        model.addAttribute("tradeRequest", new TradeRequest());
        return "stock/trading";
    }

    @PostMapping("/createBalance")
    public String createBalance() {
        String token = this.authTokenService.get(getUserId()).getValue();
        this.demoTrading.createBalance(getUserId(), token);
        return "redirect:http://localhost:8080/stockmarket/trading";
    }

    @PostMapping("/increaseBalance")
    public String increaseBalance(@ModelAttribute("sum") Double sum) {
        String token =  this.authTokenService.get(getUserId()).getValue();
        this.demoTrading.increaseBalance(getUserId(), sum, token);
        return "redirect:http://localhost:8080/stockmarket/trading";
    }

    @PostMapping("/trade")
    public String trade(@ModelAttribute("tradeRequest") TradeRequest tradeRequest) {
        String token = this.authTokenService.get(getUserId()).getValue();
        StockPrice stockPrice = this.stockMarketClient.getPrice(tradeRequest.getTicker(), token);

        demoTrading.trade(new TradeRequest(tradeRequest.getTicker(), tradeRequest.getTradeAction(),
                        tradeRequest.getQuantity(), stockPrice.getC(), getUserId()), token);

        return "redirect:http://localhost:8080/stockmarket/trading";
    }

    @GetMapping("toStockMarket")
    public String redirectToStockMarketPage(){
        return "redirect:http://localhost:9095/stockmarket/main";
    }

    @GetMapping("toStockMarketReserve")
    public String redirectToStockMarketPageReserve(){
        return "redirect:http://web:9095/stockmarket/main";
    }

    public Integer getUserId() {
        PersonDetails personDetails = (PersonDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return personDetails.getUserId();
    }
}
