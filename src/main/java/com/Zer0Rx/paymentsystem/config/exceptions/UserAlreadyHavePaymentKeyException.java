package com.Zer0Rx.paymentsystem.config.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserAlreadyHavePaymentKeyException extends RuntimeException{
    public UserAlreadyHavePaymentKeyException(){
        super("User Already have a Payment key");
    }

}
