package com.cerberus.userservice;

import com.cerberus.userservice.model.Role;
import com.cerberus.userservice.model.User;
import com.cerberus.userservice.util.Method;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@SpringBootTest
@AutoConfigureWebTestClient
@RequiredArgsConstructor
class UserTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void getUser(){
        request(Method.GET, HttpStatus.OK, 1)
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.name").isEqualTo("cerberus")
                .jsonPath("$.email").isEqualTo("cerberus@gmail.com")
                .jsonPath("$.role").isEqualTo("ROLE_USER");
    }

    @Test
    public void notFound(){
        request(Method.GET, HttpStatus.NOT_FOUND, 102332)
                .jsonPath("$.title").isEqualTo("Пользователь не найден")
                .jsonPath("$.detail").isEqualTo("Пользователь с 102332 id не найден");
    }

    @Test
    public void nameNotValid(){
        var mono = Mono.just(new User(null, "", "pochta@gmail.com", Role.ROLE_USER, "1234"));
        request(Method.POST, HttpStatus.BAD_REQUEST, mono)
                .jsonPath("$.title").isEqualTo("Валидация не пройдена")
                .jsonPath("$.detail").isEqualTo("Имя пользователя не должно быть пустым");
    }

    @Test
    public void emailNotValid(){
        var mono = Mono.just(new User(null, "dasasffd", "pochtagmail.com", Role.ROLE_USER, "1234"));
        request(Method.POST, HttpStatus.BAD_REQUEST, mono)
                .jsonPath("$.title").isEqualTo("Валидация не пройдена")
                .jsonPath("$.detail").isEqualTo("Email не должен быть пустьм и должен быть валидным");
    }

    @Test
    public void create(){
        var mono = Mono.just(new User(null, "dasasffd", "pochta@gmail.com", Role.ROLE_USER, "1234"));
        request(Method.POST, HttpStatus.OK, mono);
    }

    @Test
    public void update(){
        var mono = Mono.just(new User(null, "user", "user12@gmail.com", Role.ROLE_USER, "1234"));
        update(7, HttpStatus.OK, mono)
                .jsonPath("$.id").isEqualTo(7)
                .jsonPath("$.name").isEqualTo("user")
                .jsonPath("$.email").isEqualTo("user12@gmail.com")
                .jsonPath("$.role").isEqualTo("ROLE_USER");
    }

    @Test
    public void delete(){
        request(Method.DELETE, HttpStatus.OK, 7);
    }

    private WebTestClient.BodyContentSpec request(Method method, HttpStatus expectedStatus, Object object){
        return switch (method) {
            case GET -> this.webTestClient.get()
                    .uri("/api/v1/users/{id}", object)
                    .exchange()
                    .expectStatus().isEqualTo(expectedStatus)
                    .expectBody();
            case POST -> this.webTestClient.post()
                    .uri("/api/v1/users")
                    .body(object, User.class)
                    .exchange()
                    .expectStatus().isEqualTo(expectedStatus)
                    .expectBody();
            case DELETE -> this.webTestClient.delete()
                    .uri("/api/v1/users/{id}", object)
                    .exchange()
                    .expectStatus().isEqualTo(expectedStatus)
                    .expectBody();
        };
    }

    private WebTestClient.BodyContentSpec update(Integer id, HttpStatus expectedStatus, Object object){
        return this.webTestClient.patch()
                .uri("/api/v1/users/{id}", id)
                .body(object, User.class)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus)
                .expectBody();
    }
}
