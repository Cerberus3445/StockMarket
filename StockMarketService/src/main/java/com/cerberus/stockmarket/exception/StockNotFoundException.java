package com.cerberus.stockmarket.exception;

public class StockNotFoundException extends RuntimeException{

    private static final String message = "Акция с %d id не найдена";

    public StockNotFoundException(Integer id) {
        super(message.formatted(id));
    }
}
