package com.cerberus.demotrading;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
public class UserBalanceTest {

    @Autowired
    public WebTestClient webTestClient;

    @Test
    public void get(){
        this.webTestClient.get()
                .uri("http://localhost:5050/api/v1/trade/user/{id}", 1)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.userId").isEqualTo(1)
                .jsonPath("$.balance").isEqualTo(9031.54);
    }

    @Test
    public void create(){
        this.webTestClient.post()
                .uri("http://localhost:5050/api/v1/trade/user/{id}", 5)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.userId").isEqualTo(5)
                .jsonPath("$.balance").isEqualTo(10000.0);
    }

    @Test
    public void increase(){
        this.webTestClient.post()
                .uri("http://localhost:5050/api/v1/trade/user/3/increase?sum={sum}", 100)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.userId").isEqualTo(3)
                .jsonPath("$.balance").isEqualTo(10300.0);
    }
}
