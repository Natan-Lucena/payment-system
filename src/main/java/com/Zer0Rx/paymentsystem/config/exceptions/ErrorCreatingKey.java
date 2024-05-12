package com.Zer0Rx.paymentsystem.config.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ErrorCreatingKey  extends RuntimeException{
    public ErrorCreatingKey(){
        super("Um erro ocorreu criando a chave");
    }
}
