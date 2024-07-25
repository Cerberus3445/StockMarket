package com.cerberus.webservice.mapper;


import com.cerberus.webservice.dto.StockDto;
import com.cerberus.webservice.model.Stock;

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
