package com.cerberus.demotradingweb.client;


import com.cerberus.demotradingweb.dto.PortfolioItemDto;
import com.cerberus.demotradingweb.dto.TradeRequest;
import com.cerberus.demotradingweb.dto.TradeResponse;
import com.cerberus.demotradingweb.dto.UserBalanceDto;

import java.util.List;

public interface DemoTrading {

    TradeResponse trade(TradeRequest tradeRequest, String token);

    UserBalanceDto getBalance(Integer userId, String token);

    UserBalanceDto createBalance(Integer userId, String token);

    UserBalanceDto increaseBalance(Integer userId, Double sum, String token);

    List<PortfolioItemDto> getTradeHistory(Integer userId, String token);
}
