package com.cerberus.stockmarket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockPriceDto {

    private String ticker;

    private Double c; //currentPrice

    private Double d; //change

    private Double dp; //percentChange

    private Double h; //high

    private Double l; //low

    private Double o; //open

    private Double pc;  //previousClosePrice
}
