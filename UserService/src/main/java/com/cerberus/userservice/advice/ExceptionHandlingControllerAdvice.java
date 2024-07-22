package com.cerberus.userservice.advice;

import com.cerberus.userservice.exception.UserNotFoundException;
import com.cerberus.userservice.exception.UserValidationException;
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

    @ExceptionHandler(UserValidationException.class)
    public ProblemDetail handleException(UserValidationException userValidationException){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, userValidationException.getMessage());
        problemDetail.setTitle("Валидация не пройдена");
        problemDetail.setDetail(userValidationException.getMessage());
        return problemDetail;
    }
}
