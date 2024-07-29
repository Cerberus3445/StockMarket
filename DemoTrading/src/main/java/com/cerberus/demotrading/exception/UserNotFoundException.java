package com.cerberus.demotrading.exception;

public class UserNotFoundException extends RuntimeException{

    private final static String message = "Пользователь с %d id не найден";

    public UserNotFoundException(Integer id) {
        super(message.formatted(id));
    }
}
