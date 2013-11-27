package com.autentia.tutorial.springhateoas.soccer.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid input data")
public class InvalidInputDataException extends RuntimeException {

    public InvalidInputDataException() {}

    public InvalidInputDataException(String message) {
        super(message);
    }

}
