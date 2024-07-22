package com.cerberus.stockmarket.dto;

public record StockPriceDto(
        Double c, //currentPrice
        Double d, //change
        Double dp, //percentChange
        Double h, //high
        Double l, //low
        Double o, //open
        Double pc //previousClosePrice
) {
}
