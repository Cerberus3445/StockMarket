package com.cerberus.stockmarket;

import com.cerberus.stockmarket.dto.StockDto;
import com.cerberus.stockmarket.dto.StockPriceDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@SpringBootTest
@AutoConfigureWebTestClient
class StockTest {

    @Autowired //с RequiredArgsConstructor не работает
    private WebTestClient webTestClient;

    @Test
    public void get(){
        //by id
        getById(1, HttpStatus.OK)
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.ticker").isEqualTo("AAPL")
                .jsonPath("$.title").isEqualTo("Apple Inc");

        //by ticker
        getByTicker("TSLA",HttpStatus.OK)
                .jsonPath("$.id").isEqualTo(2)
                .jsonPath("$.ticker").isEqualTo("TSLA")
                .jsonPath("$.title").isEqualTo("Tesla Inc");
    }

    @Test
    public void notFoundId(){
        getById(23, HttpStatus.NOT_FOUND)
                .jsonPath("$.title").isEqualTo("Акция не найдена")
                .jsonPath("$.detail").isEqualTo("Акция с 23 id не найдена");
    }

    @Test
    public void notFoundTicker(){
        getByTicker("asdd", HttpStatus.NOT_FOUND)
                .jsonPath("$.title").isEqualTo("Акция не найдена")
                .jsonPath("$.detail").isEqualTo("Акция с тикером asdd не найдена");
    }

    @Test
    public void tickerValidationException(){
        var mono = Mono.just(new StockDto(null, "dasadssad", "title"));
        this.webTestClient.post()
                .uri("/api/v1/stocks")
                .body(mono, StockDto.class)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.title").isEqualTo("Ошибка валидации")
                .jsonPath("$.detail").isEqualTo("Тикер акции должен быть от 2 до 4 символов");
    }

    private WebTestClient.BodyContentSpec getById(Integer id, HttpStatus expectedStatus){
        return this.webTestClient.get()
                .uri("/api/v1/stocks/{id}",id)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus)
                .expectBody();
    }

    private WebTestClient.BodyContentSpec getByTicker(String ticker, HttpStatus expectedStatus){
        return this.webTestClient.get()
                .uri("/api/v1/stocks/ticker/{ticker}",ticker)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus)
                .expectBody();
    }

}
