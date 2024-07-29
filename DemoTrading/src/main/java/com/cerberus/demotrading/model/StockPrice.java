package com.cerberus.demotrading.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockPrice {

    private String ticker;

    private Double c; //currentPrice

    private Double d; //change

    private Double dp; //percentChange

    private Double h; //high

    private Double l; //low

    private Double o; //open

    private Double pc;  //previousClosePrice
}
