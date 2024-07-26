package com.cerberus.stockmarket.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MarketStatus {

    private String exchange;

    private String holiday;

    private Boolean isOpen;

    private String session;

    private String timezone;
}
