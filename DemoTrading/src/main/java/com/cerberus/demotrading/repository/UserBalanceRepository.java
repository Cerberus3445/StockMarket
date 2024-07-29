package com.cerberus.demotrading.repository;

import com.cerberus.demotrading.model.UserBalance;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserBalanceRepository extends ReactiveCrudRepository<UserBalance, Integer> {

    Mono<UserBalance> findByUserId(Integer userId);
}
