package com.example.petstore.controllers;

import com.example.petstore.dto.response.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(NoHandlerFoundException ex) {
        return new ApiError(HttpStatus.NOT_FOUND, "Resource not found");
    }

    // Just to not give up the whole error stack trace
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleGenericException(Exception ex) {
        // Log the error before we return
        return new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error. We are working on it.");
    }
}