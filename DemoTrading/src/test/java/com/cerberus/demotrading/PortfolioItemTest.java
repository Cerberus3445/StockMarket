package com.cerberus.demotrading;

import com.cerberus.demotrading.dto.PortfolioItemDto;
import com.cerberus.demotrading.model.TradeAction;
import com.cerberus.demotrading.model.TradeRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
class PortfolioItemTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void buy(){
        var tradeRequest = new TradeRequest("KO", TradeAction.BUY, 10, 10.0, 1);
        request(tradeRequest, HttpStatus.OK)
                .jsonPath("$.userId").isEqualTo(1)
                .jsonPath("$.ticker").isEqualTo("KO")
                .jsonPath("$.price").isEqualTo(10.0)
                .jsonPath("$.quantity").isEqualTo(10)
                .jsonPath("$.tradeAction").isEqualTo("BUY")
                .jsonPath("$.totalPrice").isEqualTo(100.0);
    }

    @Test
    public void sell(){
        var tradeRequest = new TradeRequest("KO", TradeAction.SELL, 10, 10.0, 1);
        request(tradeRequest, HttpStatus.OK)
                .jsonPath("$.userId").isEqualTo(1)
                .jsonPath("$.ticker").isEqualTo("KO")
                .jsonPath("$.price").isEqualTo(10.0)
                .jsonPath("$.quantity").isEqualTo(10)
                .jsonPath("$.tradeAction").isEqualTo("SELL")
                .jsonPath("$.totalPrice").isEqualTo(100.0);
    }

    @Test
    public void getTradeHistory(){
        this.webTestClient.get()
                .uri("http://localhost:5050/api/v1/trade/user/{id}/history", 1)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(PortfolioItemDto.class)
                .hasSize(12);
    }


    private WebTestClient.BodyContentSpec request(TradeRequest tradeRequest, HttpStatus httpStatus){
        return this.webTestClient.post()
                .uri("/api/v1/trade")
                .bodyValue(tradeRequest)
                .exchange()
                .expectStatus().isEqualTo(httpStatus)
                .expectBody();
    }
}
