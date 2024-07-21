package com.cerberus.stockmarket.advice;

import com.cerberus.stockmarket.exceptions.StockNotFoundException;
import com.cerberus.stockmarket.exceptions.StockValidationException;
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
        problemDetail.setTitle("Stock not found");
        problemDetail.setDetail(stockNotFoundException.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(StockValidationException.class)
    public ProblemDetail handleException(StockValidationException stockValidationException){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, stockValidationException.getMessage());
        problemDetail.setTitle("Stock not valid");
        problemDetail.setDetail(stockValidationException.getMessage());
        return problemDetail;
    }
}
