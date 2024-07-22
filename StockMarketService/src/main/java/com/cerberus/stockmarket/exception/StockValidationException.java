package com.cerberus.stockmarket.exception;

public class StockValidationException extends RuntimeException{

    public StockValidationException(String message) {
        super(message);
    }
}
