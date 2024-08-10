package com.fuzzy.courses.controller;

import com.fuzzy.courses.exception.FuzzyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(FuzzyException.class)
    public ProblemDetail handleFuzzyException(FuzzyException e){
        return e.toProblemDetail();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException e){

        var fieldErrors = e.getFieldErrors()
                .stream()
                .map(item -> new InvalidParam(item.getField(), item.getDefaultMessage()))
                .toList();

        var pb = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pb.setTitle("Your request parameters didn't validate.");
        pb.setProperty("Invalid-params", fieldErrors);

        return pb;
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ProblemDetail handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e){

        var pb = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pb.setTitle("The user is linked to one or more courses and cannot be deleted.");

        return pb;
    }

    private record InvalidParam(String fieldName, String reason){}

}
