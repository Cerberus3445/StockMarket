package com.cerberus.demotrading.mapper;

import com.cerberus.demotrading.dto.PortfolioItemDto;
import com.cerberus.demotrading.model.PortfolioItem;

public class PortfolioItemMapper {

    public static PortfolioItemDto toDto(PortfolioItem portfolioItem){
        return new PortfolioItemDto(
                portfolioItem.getId(),
                portfolioItem.getUserId(),
                portfolioItem.getTicker(),
                portfolioItem.getTradeAction(),
                portfolioItem.getQuantity(),
                portfolioItem.getPurchasePrice()
        );
    }
}
