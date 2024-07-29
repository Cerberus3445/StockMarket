package com.cerberus.demotrading.exception;

import reactor.core.publisher.Mono;

public class ApplicationExceptions {

    public static <T>Mono<T> invalidTradeRequest(String message){
        return Mono.error(new InvalidTradeRequestException(message));
    }

    public static <T>Mono<T> userNotFound(Integer id){
        return Mono.error(new UserNotFoundException(id));
    }
}
