package com.cerberus.demotradingweb.client.impl;

import com.cerberus.demotradingweb.client.UserClient;
import com.cerberus.demotradingweb.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;


@Component
@RequiredArgsConstructor
@Slf4j
public class UserClientImpl implements UserClient {

    private final RestClient restClient = RestClient.builder()
            .baseUrl("http://user:7070/api/v1/users")
            .build();

    @Override
    public UserDto getById(Integer id, String token) {
        log.info("getById {}", id);
        return this.restClient.get()
                .uri("/{id}", id)
                .header("Auth-Token", token)
                .retrieve()
                .body(UserDto.class);
    }

    @Override
    public UserDto getByUsernameOrEmail(String login) {
        log.info("getByEmail {}", login);
        return this.restClient.get()
                .uri("/login/{usernameOrEmail}", login)
                .retrieve()
                .body(UserDto.class);
    }

    @Override
    public UserDto register(UserDto dto) {
        log.info("create");
        return this.restClient.post()
                .uri("/register")
                .body(dto)
                .retrieve()
                .body(UserDto.class);
    }

    @Override
    public UserDto update(Integer id, UserDto dto, String token) {
        log.info("update {}", id);
        return this.restClient.patch()
                .uri("/{id}", id)
                .header("Auth-Token", token)
                .body(dto)
                .retrieve()
                .body(UserDto.class);
    }

    @Override
    public void delete(Integer id, String token) {
        log.info("delete {}", id);
         this.restClient.delete()
                .uri("/{id}", id)
                 .header("Auth-Token", token)
                .retrieve()
                .body(Void.class);
    }
}
