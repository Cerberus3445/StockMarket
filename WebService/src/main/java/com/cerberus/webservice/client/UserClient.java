package com.cerberus.webservice.client;

import com.cerberus.webservice.dto.UserDto;
import reactor.core.publisher.Mono;

public interface UserClient {

    Mono<UserDto> getById(Integer id);

    Mono<UserDto> getByEmail(String email);

    Mono<UserDto> create(Mono<UserDto> dtoMono);

    Mono<UserDto> update(Integer id, Mono<UserDto> dtoMono);

    Mono<Void> delete(Integer id);
}
