package com.Zer0Rx.paymentsystem.config.exceptions;

public class UserDoesNotHavePaymentKey extends RuntimeException{
    public UserDoesNotHavePaymentKey(){
        super("User does not have payment key");
    }
}
