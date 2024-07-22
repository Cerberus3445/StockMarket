package com.cerberus.stockmarket.advice;

import com.cerberus.stockmarket.exception.StockNotFoundException;
import com.cerberus.stockmarket.exception.StockValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlingControllerAdvice {


    @ExceptionHandler(StockNotFoundException.class)
    public ProblemDetail handleException(StockNotFoundException stockNotFoundException){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, stockNotFoundException.getMessage());
        problemDetail.setTitle("Акция не найдена");
        problemDetail.setDetail(stockNotFoundException.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(StockValidationException.class)
    public ProblemDetail handleException(StockValidationException stockValidationException){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, stockValidationException.getMessage());
        problemDetail.setTitle("Ошибка валидации");
        problemDetail.setDetail(stockValidationException.getMessage());
        return problemDetail;
    }
}
