package com.cerberus.demotrading.service.impl;

import com.cerberus.demotrading.dto.UserBalanceDto;
import com.cerberus.demotrading.exception.ApplicationExceptions;
import com.cerberus.demotrading.mapper.EntityDtoMapper;
import com.cerberus.demotrading.model.UserBalance;
import com.cerberus.demotrading.repository.UserBalanceRepository;
import com.cerberus.demotrading.service.UserBalanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserBalanceServiceImpl implements UserBalanceService {

    private final UserBalanceRepository userBalanceRepository;

    @Override
    public Mono<UserBalanceDto> get(Integer userId) {
        log.info("get {}", userId);
        return this.userBalanceRepository.findByUserId(userId)
                .map(EntityDtoMapper::toDto);
    }

    @Override
    public Mono<UserBalanceDto> create(Integer userId) {
        log.info("create {}", userId);
        return this.userBalanceRepository.save(new UserBalance(null, userId, 10_000.0))
                .map(EntityDtoMapper::toDto);
    }

    @Override
    public Mono<UserBalanceDto> increase(Integer userId, Double sum) {
        log.info("increase id : {}, sum : {}", userId, sum);
        return this.userBalanceRepository.findByUserId(userId)
                .switchIfEmpty(ApplicationExceptions.userNotFound(userId))
                .doOnNext(userBalance -> userBalance.setBalance(userBalance.getBalance() + sum))
                .flatMap(this.userBalanceRepository::save)
                .map(EntityDtoMapper::toDto);
    }
}
