package com.cerberus.userservice.service.impl;

import com.cerberus.userservice.dto.AuthTokenDto;
import com.cerberus.userservice.filter.Category;
import com.cerberus.userservice.mapper.EntityDtoMapper;
import com.cerberus.userservice.model.AuthToken;
import com.cerberus.userservice.model.Role;
import com.cerberus.userservice.repository.AuthTokenRepository;
import com.cerberus.userservice.service.AuthTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthTokenServiceImpl implements AuthTokenService {

    private final AuthTokenRepository authTokenRepository;

    @Override
    public Mono<AuthTokenDto> create(Long userId, Role userRole) {
        return switch (userRole){
            case ROLE_USER ->  this.authTokenRepository.save(generateToken(userId, Category.STANDARD))
                    .map(EntityDtoMapper::toDto);
            case ROLE_PRIME -> this.authTokenRepository.save(generateToken(userId, Category.PRIME))
                    .map(EntityDtoMapper::toDto);
            case ROLE_ADMIN -> this.authTokenRepository.save(generateToken(userId, Category.ADMIN))
                    .map(EntityDtoMapper::toDto);
        };
    }

    @Override
    public Mono<AuthTokenDto> getByValue(String token) {
        log.info("getByValue {}", token);
        return this.authTokenRepository.findByValue(token)
                .map(EntityDtoMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long userId) {
        return this.authTokenRepository.deleteWithUserId(userId)
                .then();
    }

    private AuthToken generateToken(Long userId, Category category){
        SecureRandom secureRandom = new SecureRandom();
        Base64.Encoder base64Encoder = Base64.getUrlEncoder();
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return new AuthToken(null, userId, base64Encoder.encodeToString(randomBytes), category);
    }
}
