package com.cerberus.demotradingweb.service.impl;

import com.cerberus.demotradingweb.model.AuthToken;
import com.cerberus.demotradingweb.repository.AuthTokenRepository;
import com.cerberus.demotradingweb.service.AuthTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthTokenServiceImpl implements AuthTokenService {

    private final AuthTokenRepository authTokenRepository;

    @Override
    public AuthToken get(Integer id) {
        return this.authTokenRepository.findById(id).orElseThrow(() -> new RuntimeException("Токен отсутствует"));
    }
}
