package com.cerberus.webservice;

import com.cerberus.webservice.client.StockMarketClient;
import com.cerberus.webservice.dto.StockDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@Slf4j
class StockMarketClientTest {

    @Autowired
    private StockMarketClient stockMarketClient;

    @Test
    public void getStockRecommendation(){
        this.stockMarketClient.getStockRecommendation("AMZN")
                .doOnNext(stockRecommendation -> log.info("получено: {}", stockRecommendation))
                .as(StepVerifier::create)
                .expectNextCount(4)
                .expectComplete()
                .verify();
    }

    @Test
    public void getStock(){
        this.stockMarketClient.getStock("AMZN")
                .doOnNext(stockDto -> log.info("получено: {}", stockDto))
                .as(StepVerifier::create)
                .assertNext(stockDto -> Assertions.assertEquals("Amazon.com Inc", stockDto.title()))
                .expectComplete()
                .verify();
    }

    @Test
    public void getAllStock(){
        this.stockMarketClient.getAllStock()
                .doOnNext(stockDto -> log.info("получено: {}", stockDto))
                .as(StepVerifier::create)
                .expectNextCount(9)
                .expectComplete()
                .verify();
    }

    @Test
    public void create(){
        var stockDto = Mono.just(new StockDto(null, "CVX", "Chevron Corp"));

        this.stockMarketClient.createStock(stockDto)
                .as(StepVerifier::create)
                .assertNext(s -> Assertions.assertEquals("Chevron Corp", s.title()))
                .expectComplete()
                .verify();
    }

    @Test
    public void update(){
        var stockDto = Mono.just(new StockDto(16, "NKE", "Nike Company"));

        this.stockMarketClient.updateStock(16, stockDto)
                .as(StepVerifier::create)
                .assertNext(s -> Assertions.assertEquals("Nike Company", s.title()))
                .expectComplete()
                .verify();
    }

    @Test
    public void delete(){
        this.stockMarketClient.deleteStock(10)
                .then(Mono.defer(() -> this.stockMarketClient.getStock("SBER")))
                .as(StepVerifier::create)
                .expectError()
                .verify();
    }
}
