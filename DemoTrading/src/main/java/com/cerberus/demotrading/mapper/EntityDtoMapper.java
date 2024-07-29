package com.cerberus.demotrading.mapper;

import com.cerberus.demotrading.dto.PortfolioItemDto;
import com.cerberus.demotrading.dto.UserBalanceDto;
import com.cerberus.demotrading.model.PortfolioItem;
import com.cerberus.demotrading.model.UserBalance;

public class EntityDtoMapper {

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

    public static UserBalanceDto toDto(UserBalance userBalance){
        return new UserBalanceDto(
                userBalance.getId(),
                userBalance.getUserId(),
                userBalance.getBalance()
        );
    }
}
