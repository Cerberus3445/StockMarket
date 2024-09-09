package com.cerberus.demotradingweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradeRequest {

    private String ticker;

    private TradeAction tradeAction;

    private Integer quantity;

    private Double price;

    private Integer userId;

    public Double totalPrice(){
        return price * quantity;
    }
}
