package com.cerberus.userservice.exception;

import reactor.core.publisher.Mono;

public class ApplicationExceptions {

    public static <T>Mono<T> userNotFound(Long id){
        return Mono.error(new UserNotFoundException(id));
    }

    public static <T>Mono<T> userNotFound(String email){
        return Mono.error(new UserNotFoundException(email));
    }

    public static <T>Mono<T> userNameValidation(){
        return Mono.error(new UserValidationException("Имя пользователя не должно быть пустым"));
    }

    public static <T>Mono<T> userEmailValidation(){
        return Mono.error(new UserValidationException("Email не должен быть пустьм и должен быть валидным"));
    }
}
