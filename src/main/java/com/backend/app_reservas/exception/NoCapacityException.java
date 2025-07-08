package com.backend.app_reservas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class NoCapacityException extends RuntimeException {
    public NoCapacityException(String message) {
        super(message);
    }
}
