package com.cerberus.webservice.client.impl;

import com.cerberus.webservice.client.UserClient;
import com.cerberus.webservice.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserClientImpl implements UserClient {

    private final WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:7070/api/v1/users")
            .build();

    @Override
    public Mono<UserDto> getById(Integer id) {
        log.info("getById {}", id);
        return this.webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(UserDto.class);
    }

    @Override
    public Mono<UserDto> getByEmail(String email) {
        log.info("getByEmail {}", email);
        return this.webClient.get()
                .uri("/email/{email}", email)
                .retrieve()
                .bodyToMono(UserDto.class);
    }

    @Override
    public Mono<UserDto> create(Mono<UserDto> dtoMono) {
        log.info("create");
        return this.webClient.post()
                .body(dtoMono, UserDto.class)
                .retrieve()
                .bodyToMono(UserDto.class);
    }

    @Override
    public Mono<UserDto> update(Integer id, Mono<UserDto> dtoMono) {
        log.info("update {}", id);
        return this.webClient.patch()
                .uri("/{id}", id)
                .body(dtoMono, UserDto.class)
                .retrieve()
                .bodyToMono(UserDto.class);
    }

    @Override
    public Mono<Void> delete(Integer id) {
        log.info("delete {}", id);
        return this.webClient.delete()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
