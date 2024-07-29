package com.cerberus.demotrading.service.impl;

import com.cerberus.demotrading.dto.PortfolioItemDto;
import com.cerberus.demotrading.exception.ApplicationExceptions;
import com.cerberus.demotrading.mapper.PortfolioItemMapper;
import com.cerberus.demotrading.model.*;
import com.cerberus.demotrading.repository.PortfolioItemRepository;
import com.cerberus.demotrading.repository.UserBalanceRepository;
import com.cerberus.demotrading.service.PortfolioItemService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PortfolioItemServiceImpl implements PortfolioItemService {

    private final static Logger log = LoggerFactory.getLogger(PortfolioItemServiceImpl.class);

    private final PortfolioItemRepository portfolioItemRepository;

    private final UserBalanceRepository userBalanceRepository;


    @Override
    public Mono<TradeResponse> trade(TradeRequest tradeRequest) {
        log.info("trade");
       return switch (tradeRequest.tradeAction()){
           case BUY -> buy(tradeRequest);
           case SELL -> sell(tradeRequest);
       };
    }

    @Override
    public Flux<PortfolioItemDto> getTradeHistory(Integer userId) {
        return this.portfolioItemRepository.findByUserId(userId)
                .map(PortfolioItemMapper::toDto);
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
                .flatMap(i -> this.portfolioItemRepository
                        .findFirstByUserIdAndTickerAndQuantityAndTradeAction(tradeRequest.userId(), tradeRequest.ticker(), tradeRequest.quantity(), TradeAction.BUY))
                .switchIfEmpty(ApplicationExceptions.invalidTradeRequest("Ошибка продажи: проверьте количество акций или тикер акции"))
                .doOnNext(portfolioItem -> portfolioItem.setTradeAction(TradeAction.SELL))
                .flatMap(this.portfolioItemRepository::save)
                .flatMap(i -> this.userBalanceRepository.findByUserId(tradeRequest.userId()))
                .doOnNext(userBalance -> userBalance.setBalance(userBalance.getBalance() + tradeRequest.totalPrice()))
                .flatMap(this.userBalanceRepository::save)
                .map(i -> new TradeResponse(tradeRequest.userId(), tradeRequest.ticker(), tradeRequest.price(),
                        tradeRequest.quantity(), tradeRequest.tradeAction(), tradeRequest.totalPrice()));
    }
}
