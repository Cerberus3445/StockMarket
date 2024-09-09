package com.cerberus.userservice.service;

import com.cerberus.userservice.dto.AuthTokenDto;
import com.cerberus.userservice.model.Role;
import reactor.core.publisher.Mono;

public interface AuthTokenService {

    Mono<AuthTokenDto> create(Long userId, Role userRole);

    Mono<AuthTokenDto> getByValue(String token);

    Mono<Void> delete(Long userId);

    Mono<AuthTokenDto> getByUserId(Long userId);
}
