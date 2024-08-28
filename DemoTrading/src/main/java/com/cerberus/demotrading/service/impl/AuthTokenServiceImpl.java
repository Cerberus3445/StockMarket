package com.cerberus.demotrading.service.impl;

import com.cerberus.demotrading.model.AuthToken;
import com.cerberus.demotrading.repository.AuthTokenRepository;
import com.cerberus.demotrading.service.AuthTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthTokenServiceImpl implements AuthTokenService {

    private final AuthTokenRepository authTokenRepository;

    @Override
    public Mono<AuthToken> getByValue(String token) {
        log.info("getByValue {}", token);
        return this.authTokenRepository.findByValue(token);
    }
}
