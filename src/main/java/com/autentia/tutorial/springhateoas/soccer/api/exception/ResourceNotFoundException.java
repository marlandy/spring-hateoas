package com.autentia.tutorial.springhateoas.soccer.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource not found in soccer-api")
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {}

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
