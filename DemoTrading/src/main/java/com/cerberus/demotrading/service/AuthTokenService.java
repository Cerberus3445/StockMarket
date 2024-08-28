package com.cerberus.demotrading.service;
import com.cerberus.demotrading.model.AuthToken;
import reactor.core.publisher.Mono;

public interface AuthTokenService {

    Mono<AuthToken> getByValue(String token);
}
