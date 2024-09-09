package com.cerberus.demotradingweb.dto;

public record PortfolioItemDto(
        Integer id,
        Integer userId,
        String ticker,
        TradeAction tradeAction,
        Integer quantity,
        Double purchasePrice
) {

    @Override
    public String toString() {
        return "Тикер акции - %s, действие - %s, количество - %d, цена покупки - %f"
                .formatted(ticker, tradeAction, quantity, purchasePrice);
    }
}
