package com.cerberus.stockmarket.exceptions;

public class StockNotFoundException extends RuntimeException{

    private static final String message = "Пользователь с %d id не найден";

    public StockNotFoundException(Integer id) {
        super(message.formatted(id));
    }
}
