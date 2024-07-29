package com.cerberus.demotrading.advice;

import com.cerberus.demotrading.exception.InvalidTradeRequestException;
import com.cerberus.demotrading.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlingControllerAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    public ProblemDetail handleException(UserNotFoundException userNotFoundException){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, userNotFoundException.getMessage());
        problemDetail.setTitle("Пользователь не найден");
        problemDetail.setDetail(userNotFoundException.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(InvalidTradeRequestException.class)
    public ProblemDetail handleException(InvalidTradeRequestException invalidTradeRequestException){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, invalidTradeRequestException.getMessage());
        problemDetail.setTitle("Ошибка");
        problemDetail.setDetail(invalidTradeRequestException.getMessage());
        return problemDetail;
    }
}
