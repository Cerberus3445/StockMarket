package com.cerberus.userservice.service;

import com.cerberus.userservice.dto.UserDto;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<UserDto> getById(Integer id);

    Mono<UserDto> getByEmail(String email);

    Mono<UserDto> create(Mono<UserDto> dtoMono);

    Mono<UserDto> update(Integer id, Mono<UserDto> dtoMono);

    Mono<Boolean> delete(Integer id);
}
