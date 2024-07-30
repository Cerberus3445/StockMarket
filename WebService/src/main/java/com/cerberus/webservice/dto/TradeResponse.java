package com.cerberus.webservice.dto;

public record TradeResponse(
        Integer userId,
        String ticker,
        Double price,
        Integer quantity,
        TradeAction tradeAction,
        Double totalPrice
) {
}
