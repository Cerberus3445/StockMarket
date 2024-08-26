package com.cerberus.userservice.repository;

import com.cerberus.userservice.model.AuthToken;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AuthTokenRepository extends ReactiveCrudRepository<AuthToken, Long> {

    @Modifying
    @Query("delete from stock_market.tokens where user_id=:id")
    Mono<Boolean> deleteWithUserId(Long id);

    Mono<AuthToken> findByValue(String token);
}
