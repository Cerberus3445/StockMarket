package com.cerberus.userservice.exception;

public class UserValidationException extends RuntimeException{

    public UserValidationException(String message) {
        super(message);
    }
}
