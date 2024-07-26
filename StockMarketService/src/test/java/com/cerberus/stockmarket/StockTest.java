package com.cerberus.stockmarket;

import com.cerberus.stockmarket.dto.StockDto;
import com.cerberus.stockmarket.dto.StockPriceDto;
import com.cerberus.stockmarket.util.Method;
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
        request(Method.GET_BY_ID,  HttpStatus.OK, 1)
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.ticker").isEqualTo("AAPL")
                .jsonPath("$.title").isEqualTo("Apple Inc");

        //by ticker
        request(Method.GET_BY_TICKER,  HttpStatus.OK, "TSLA")
                .jsonPath("$.id").isEqualTo(2)
                .jsonPath("$.ticker").isEqualTo("TSLA")
                .jsonPath("$.title").isEqualTo("Tesla Inc");
    }

    @Test
    public void notFoundId(){
        request(Method.GET_BY_ID,  HttpStatus.NOT_FOUND, 23)
                .jsonPath("$.title").isEqualTo("Акция не найдена")
                .jsonPath("$.detail").isEqualTo("Акция с 23 id не найдена");
    }

    @Test
    public void notFoundTicker(){
        request(Method.GET_BY_TICKER,  HttpStatus.NOT_FOUND, "asdd")
                .jsonPath("$.title").isEqualTo("Акция не найдена")
                .jsonPath("$.detail").isEqualTo("Акция с тикером asdd не найдена");
    }

    @Test
    public void tickerValidationException(){
        var mono = Mono.just(new StockDto(null, "dasadssad", "title"));
        request(Method.POST, HttpStatus.BAD_REQUEST, mono)
                .jsonPath("$.title").isEqualTo("Ошибка валидации")
                .jsonPath("$.detail").isEqualTo("Тикер акции должен быть от 2 до 4 символов");

    }

    @Test
    public void create(){
        var mono = Mono.just(new StockDto(null, "TTTT", "title"));
        request(Method.POST, HttpStatus.OK, mono)
                .jsonPath("$.id").isNotEmpty();
    }

    @Test
    public void update(){
        var mono = Mono.just(new StockDto(null, "TEET", "test stock"));
        update(14, HttpStatus.OK, mono)
                .jsonPath("$.ticker").isEqualTo("TEET")
                .jsonPath("$.title").isEqualTo("test stock");
    }

    @Test
    public void delete(){
        request(Method.DELETE, HttpStatus.OK, 14);
    }

    private WebTestClient.BodyContentSpec request(Method method, HttpStatus expectedStatus, Object object){
        return switch (method) {
            case GET_BY_ID -> this.webTestClient.get()
                    .uri("/api/v1/stocks/{id}", object)
                    .exchange()
                    .expectStatus().isEqualTo(expectedStatus)
                    .expectBody();
            case GET_BY_TICKER -> this.webTestClient.get()
                    .uri("/api/v1/stocks/ticker/{ticker}", object)
                    .exchange()
                    .expectStatus().isEqualTo(expectedStatus)
                    .expectBody();
            case POST -> this.webTestClient.post()
                    .uri("/api/v1/stocks")
                    .body(object, StockDto.class)
                    .exchange()
                    .expectStatus().isEqualTo(expectedStatus)
                    .expectBody();
            case DELETE -> this.webTestClient.delete()
                    .uri("/api/v1/stocks/{id}", object)
                    .exchange()
                    .expectStatus().isEqualTo(expectedStatus)
                    .expectBody();
        };
    }

    private WebTestClient.BodyContentSpec update(Integer id, HttpStatus expectedStatus, Object object){
        return this.webTestClient.patch()
                .uri("/api/v1/stocks/{id}", id)
                .body(object, StockDto.class)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus)
                .expectBody();
    }

}
