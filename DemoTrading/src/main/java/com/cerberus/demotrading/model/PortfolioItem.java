package com.cerberus.demotrading.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("portfolio_items")
public class PortfolioItem {

    @Id
    private Integer id;

    private Integer userId;

    private String ticker;

    private TradeAction tradeAction;

    private Integer quantity;

    private Double purchasePrice;
}
