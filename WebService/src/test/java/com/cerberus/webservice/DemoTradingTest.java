package com.cerberus.webservice;

import com.cerberus.webservice.client.DemoTrading;
import com.cerberus.webservice.dto.TradeAction;
import com.cerberus.webservice.dto.TradeRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@Slf4j
@SpringBootTest
public class DemoTradingTest {

    @Autowired
    private DemoTrading demoTrading;

    @Test
    public void getUserBalance(){
        this.demoTrading.getBalance(1)
                .doOnNext(userBalanceDto -> log.info("получено: {}", userBalanceDto))
                .as(StepVerifier::create)
                .assertNext(userBalanceDto -> Assertions.assertEquals(9031.54, userBalanceDto.balance()))
                .expectComplete()
                .verify();
    }

    @Test
    public void createBalance(){
        this.demoTrading.createBalance(8)
                .doOnNext(userBalanceDto -> log.info("получено: {}", userBalanceDto))
                .as(StepVerifier::create)
                .assertNext(userBalanceDto -> Assertions.assertEquals(10000.0, userBalanceDto.balance()))
                .expectComplete()
                .verify();
    }

    @Test
    public void increaseBalance(){
        this.demoTrading.increaseBalance(8, 100.0)
                .doOnNext(userBalanceDto -> log.info("получено: {}", userBalanceDto))
                .as(StepVerifier::create)
                .assertNext(userBalanceDto -> Assertions.assertEquals(10100.0, userBalanceDto.balance()))
                .expectComplete()
                .verify();
    }

    @Test
    public void getTradeHistory(){
        this.demoTrading.getTradeHistory(8)
                .doOnNext(userBalanceDto -> log.info("получено: {}", userBalanceDto))
                .as(StepVerifier::create)
                .expectNextCount(3)
                .expectComplete()
                .verify();
    }

    @Test
    public void buyStocks(){
        var tradeRequest = Mono.just(new TradeRequest("AMZN", TradeAction.BUY, 10, 177.59, 1));
        this.demoTrading.trade(tradeRequest)
                .doOnNext(tradeResponse -> log.info("получено: {}", tradeResponse))
                .as(StepVerifier::create)
                .assertNext(tradeResponse -> Assertions.assertEquals(1775.9, tradeResponse.totalPrice()))
                .expectComplete()
                .verify();
    }

    @Test
    public void sellStocks(){
        var tradeRequest = Mono.just(new TradeRequest("AMZN", TradeAction.SELL, 10, 177.59, 1));
        this.demoTrading.trade(tradeRequest)
                .doOnNext(tradeResponse -> log.info("получено: {}", tradeResponse))
                .as(StepVerifier::create)
                .assertNext(tradeResponse -> Assertions.assertEquals(1775.9, tradeResponse.totalPrice()))
                .expectComplete()
                .verify();
    }

    @Test
    public void notEnoughMoneyException(){
        var tradeRequest = Mono.just(new TradeRequest("AMZN", TradeAction.BUY, 1000, 177.59, 1));
        this.demoTrading.trade(tradeRequest)
                .doOnNext(tradeResponse -> log.info("получено: {}", tradeResponse))
                .as(StepVerifier::create)
                .expectError()
                .verify();
    }
}
