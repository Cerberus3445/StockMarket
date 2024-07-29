package com.cerberus.demotrading.service;

import com.cerberus.demotrading.dto.UserBalanceDto;
import reactor.core.publisher.Mono;

public interface UserBalanceService {

    Mono<UserBalanceDto> get(Integer userId);

    Mono<UserBalanceDto> create(Integer userId);

    Mono<UserBalanceDto> increase(Integer userId, Double sum);
}
