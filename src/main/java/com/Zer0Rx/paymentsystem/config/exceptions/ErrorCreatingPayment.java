package com.Zer0Rx.paymentsystem.config.exceptions;

public class ErrorCreatingPayment extends RuntimeException {
    public ErrorCreatingPayment(){
        super("An error has ocurred creating the payment");
    }
}
