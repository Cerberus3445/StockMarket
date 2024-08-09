package com.cerberus.userservice.repository;

import com.cerberus.userservice.dto.UserDto;
import com.cerberus.userservice.model.User;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Integer> {

    Mono<User> findByEmail(String email);

    @Modifying
    @Query("delete from stock_market.user where id=:id")
    Mono<Boolean> deleteWithId(Integer id);
}
