package com.cerberus.stockmarket.validator;

import com.cerberus.stockmarket.dto.StockDto;
import com.cerberus.stockmarket.exception.ApplicationExceptions;
import reactor.core.publisher.Mono;

import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class RequestValidator {

    public static UnaryOperator<Mono<StockDto>> validate() {
        return mono -> mono.filter(hasName())
                .switchIfEmpty(ApplicationExceptions.stockValidationName())
                .filter(hasValidTicker())
                .switchIfEmpty(ApplicationExceptions.stockValidationTicker());
    }

    private static Predicate<StockDto> hasName(){
        return dto -> !dto.title().isEmpty();
    }

    private static Predicate<StockDto> hasValidTicker() {
        return dto -> dto.ticker().length() >= 2 && dto.ticker().length() <= 4;
    }

}
