package com.cerberus.demotrading.service.impl;

import com.cerberus.demotrading.dto.PortfolioItemDto;
import com.cerberus.demotrading.exception.ApplicationExceptions;
import com.cerberus.demotrading.mapper.EntityDtoMapper;
import com.cerberus.demotrading.model.*;
import com.cerberus.demotrading.repository.PortfolioItemRepository;
import com.cerberus.demotrading.repository.UserBalanceRepository;
import com.cerberus.demotrading.service.PortfolioItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class PortfolioItemServiceImpl implements PortfolioItemService {


    private final PortfolioItemRepository portfolioItemRepository;

    private final UserBalanceRepository userBalanceRepository;


    @Override
    public Mono<TradeResponse> trade(TradeRequest tradeRequest) {
        log.info("trade {}", tradeRequest);
       return switch (tradeRequest.tradeAction()){
           case BUY -> buy(tradeRequest);
           case SELL -> sell(tradeRequest);
       };
    }

    @Override
    public Flux<PortfolioItemDto> getTradeHistory(Integer userId) {
        log.info("getTradeHistory {}", userId);
        return this.portfolioItemRepository.findByUserId(userId)
                .map(EntityDtoMapper::toDto);
    }

    private Mono<TradeResponse> buy(TradeRequest tradeRequest) {
        return this.userBalanceRepository.findByUserId(tradeRequest.userId())
                .switchIfEmpty(ApplicationExceptions.userNotFound(tradeRequest.userId()))
                .filter(userBalance -> userBalance.getBalance() > tradeRequest.totalPrice())
                .switchIfEmpty(ApplicationExceptions.invalidTradeRequest("Недостаточный баланс"))
                .map(userBalance -> new UserBalance(userBalance.getId(), userBalance.getUserId(), userBalance.getBalance() - tradeRequest.totalPrice()))
                .flatMap(this.userBalanceRepository::save)
                .flatMap(userBalance -> this.portfolioItemRepository.save(new PortfolioItem(null, userBalance.getUserId(),
                        tradeRequest.ticker(), tradeRequest.tradeAction(), tradeRequest.quantity(), tradeRequest.price())))
                .map(i -> new TradeResponse(tradeRequest.userId(), tradeRequest.ticker(), tradeRequest.price(),
                        tradeRequest.quantity(), tradeRequest.tradeAction(), tradeRequest.totalPrice()));
    }

    private Mono<TradeResponse> sell(TradeRequest tradeRequest) {
        return this.userBalanceRepository.findByUserId(tradeRequest.userId())
                .switchIfEmpty(ApplicationExceptions.userNotFound(tradeRequest.userId()))
                .flatMap(i -> this.portfolioItemRepository.findPortfolioItem(tradeRequest.userId(),
                        tradeRequest.ticker(),TradeAction.BUY, tradeRequest.quantity()))
                .filter(portfolioItem -> tradeRequest.quantity() <= portfolioItem.getQuantity())
                .switchIfEmpty(ApplicationExceptions.invalidTradeRequest("Ошибка продажи: проверьте количество акций или тикер акции"))
                .flatMap(portfolioItem -> {
                   if(portfolioItem.getQuantity() == tradeRequest.quantity()){
                       portfolioItem.setTradeAction(TradeAction.SELL);
                       return this.portfolioItemRepository.save(portfolioItem);
                   } else {
                       portfolioItem.setQuantity(portfolioItem.getQuantity() - tradeRequest.quantity());
                      return this.portfolioItemRepository.save(portfolioItem).flatMap(pr -> this.portfolioItemRepository.save(new PortfolioItem(null, tradeRequest.userId(), tradeRequest.ticker(),
                              tradeRequest.tradeAction(), tradeRequest.quantity(), tradeRequest.totalPrice())));
                   }
                })
                .flatMap(i -> this.userBalanceRepository.findByUserId(tradeRequest.userId()))
                .doOnNext(userBalance -> userBalance.setBalance(userBalance.getBalance() + tradeRequest.totalPrice()))
                .flatMap(this.userBalanceRepository::save)
                .map(i -> new TradeResponse(tradeRequest.userId(), tradeRequest.ticker(), tradeRequest.price(),
                        tradeRequest.quantity(), tradeRequest.tradeAction(), tradeRequest.totalPrice()));
    }
}
