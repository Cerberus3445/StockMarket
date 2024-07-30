package com.cerberus.webservice.dto;

public record PortfolioItemDto(
         Integer id,
         Integer userId,
         String ticker,
         TradeAction tradeAction,
         Integer quantity,
         Double purchasePrice
) {
}
