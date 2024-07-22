package com.cerberus.userservice.exception;

public class UserNotFoundException extends RuntimeException{

    private static final String messageId = "Пользователь с %d id не найден";

    private static final String message = "Пользователь с почтой %s не найден";

    public UserNotFoundException(Integer id) {
        super(messageId.formatted(id));
    }


    public UserNotFoundException(String email) {
        super(message.formatted(email));
    }
}
