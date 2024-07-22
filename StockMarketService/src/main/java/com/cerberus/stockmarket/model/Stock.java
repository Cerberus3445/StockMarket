package com.cerberus.stockmarket.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("Stocks")
public class Stock {

    @Id
    private Integer id;

    private String ticker;

    private String title;
}
