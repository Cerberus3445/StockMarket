package com.cerberus.webservice.dto;

public record StockDto(
        Integer id,
        String ticker,
        String title
) {
}
