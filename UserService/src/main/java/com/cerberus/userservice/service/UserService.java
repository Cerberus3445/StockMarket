package com.cerberus.userservice.service;

import com.cerberus.userservice.dto.AuthTokenDto;
import com.cerberus.userservice.dto.UserDto;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<UserDto> getById(Long id);

    Mono<UserDto> getByUsernameOrEmail(String usernameOrEmail);

    Mono<Void> create(Mono<UserDto> dtoMono);

    Mono<UserDto> update(Long id, Mono<UserDto> dtoMono);

    Mono<Boolean> delete(Long id);
}
