package com.cerberus.demotradingweb.client;

import com.cerberus.demotradingweb.model.StockPrice;

public interface StockMarketClient {

    StockPrice getPrice(String ticker, String token);
}
