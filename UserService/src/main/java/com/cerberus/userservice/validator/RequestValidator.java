package com.cerberus.userservice.validator;

import com.cerberus.userservice.dto.UserDto;
import com.cerberus.userservice.exception.ApplicationExceptions;
import reactor.core.publisher.Mono;

import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class RequestValidator {

    public static UnaryOperator<Mono<UserDto>> validate() {
        return mono -> mono.filter(hasName())
                .switchIfEmpty(ApplicationExceptions.userNameValidation())
                .filter(hasValidEmail())
                .switchIfEmpty(ApplicationExceptions.userEmailValidation());
    }

    private static Predicate<UserDto> hasName(){
        return dto -> !dto.getName().isEmpty();
    }

    private static Predicate<UserDto> hasValidEmail() {
        return dto -> !dto.getEmail().isEmpty() && dto.getEmail().contains("@");
    }
}
