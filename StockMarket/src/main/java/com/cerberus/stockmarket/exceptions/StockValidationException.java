package com.cerberus.stockmarket.exceptions;

public class StockValidationException extends RuntimeException{

    public StockValidationException(String message) {
        super(message);
    }
}
