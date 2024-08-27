package com.cerberus.stockmarket.service;
import com.cerberus.stockmarket.model.AuthToken;
import reactor.core.publisher.Mono;

public interface AuthTokenService {

    Mono<AuthToken> getByValue(String token);
}
