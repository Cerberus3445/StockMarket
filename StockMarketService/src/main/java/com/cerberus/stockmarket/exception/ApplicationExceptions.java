package com.cerberus.stockmarket.exception;

import reactor.core.publisher.Mono;


public class ApplicationExceptions {

    public static <T>Mono<T> stockNotFound(Integer id){
        return Mono.error(new StockNotFoundException(id));
    }

    public static <T>Mono<T> stockNotFound(String ticker){
        return Mono.error(new StockNotFoundException(ticker));
    }

    public static <T>Mono<T> stockValidationName(){
        return Mono.error(new StockValidationException("Название акции не должно быть пустным"));
    }

    public static <T>Mono<T> stockValidationTicker(){
        return Mono.error(new StockValidationException("Тикер акции должен быть от 2 до 4 символов"));
    }

    public static <T>Mono<T> stockAlreadyExists(){
        return Mono.error(new StockAlreadyExistsException());
    }
}
