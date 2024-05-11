package com.Zer0Rx.paymentsystem.config.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException(String message ) {
        super(message);
    }
}