package com.cerberus.stockmarket.mapper;

import com.cerberus.stockmarket.dto.StockDto;
import com.cerberus.stockmarket.model.Stock;

public class StockMapper {

    public static StockDto toDto(Stock stock){
        return new StockDto(
                stock.getId(),
                stock.getTicker(),
                stock.getTitle()
        );
    }

    public static Stock toEntity(StockDto stockDto){
        Stock stock = new Stock();
        stock.setId(stockDto.id());
        stock.setTicker(stockDto.ticker());
        stock.setTitle(stockDto.title());
        return stock;
    }
}
