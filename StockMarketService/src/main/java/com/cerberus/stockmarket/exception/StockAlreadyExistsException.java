package com.cerberus.stockmarket.exception;

public class StockAlreadyExistsException extends RuntimeException{

    private final static String message = "Акция с этим тикером уже существует";

    public StockAlreadyExistsException() {
        super(message);
    }
}
