package com.cerberus.demotrading.model;

public record TradeRequest(
        String ticker,
        TradeAction tradeAction,
        Integer quantity,
        Double price,
        Integer userId
) {

    public Double totalPrice(){
        return price * quantity;
    }
}
