package com.cerberus.stockmarket.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class StockRecommendation {

    private Integer buy;

    private Integer hold;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate period;

    private Integer sell;

    private Integer strongBuy;

    private Integer strongSell;

    private String ticker;
}
