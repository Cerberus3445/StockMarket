package com.cerberus.demotrading.dto;

import com.cerberus.demotrading.model.TradeAction;

public record PortfolioItemDto(
         Integer id,
         Integer userId,
         String ticker,
         TradeAction tradeAction,
         Integer quantity,
         Double purchasePrice
) {
}
