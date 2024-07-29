package com.cerberus.demotrading.model;

public record TradeResponse(
        Integer userId,
        String ticker,
        Double price,
        Integer quantity,
        TradeAction action,
        Double totalPrice
) {
}
