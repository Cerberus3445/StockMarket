package com.cerberus.webservice.client;

import com.cerberus.webservice.dto.UserDto;
import reactor.core.publisher.Mono;

public interface UserClient {

    Mono<UserDto> getById(Integer id);

    Mono<UserDto> getByUsernameOrEmail(String email);

    Mono<UserDto> register(Mono<UserDto> dtoMono);

    Mono<UserDto> update(Integer id, Mono<UserDto> dtoMono);

    Mono<Void> delete(Integer id);
}
