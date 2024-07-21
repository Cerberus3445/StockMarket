package com.cerberus.stockmarket.dto;

public record StockDto(
        Integer id,
        String ticker,
        String title
) {
}
