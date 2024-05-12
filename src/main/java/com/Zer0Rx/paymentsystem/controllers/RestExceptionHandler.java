package com.Zer0Rx.paymentsystem.controllers;



import org.json.JSONObject;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.Zer0Rx.paymentsystem.config.exceptions.UserAlreadyHavePaymentKeyException;
import com.Zer0Rx.paymentsystem.config.exceptions.UserDoesNotHavePaymentKey;

import br.com.efi.efisdk.exceptions.EfiPayException;


@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice()
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler()
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleGenericException(Exception ex ){
        JSONObject res = new JSONObject();
        System.out.println(ex.getMessage());
        res.put("message", "Erro interno do servidor");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res.toString());
    }
    @ExceptionHandler(EfiPayException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleEfiExceptions(EfiPayException e){
        JSONObject res = new JSONObject();
        res.put("message", "Ocorreu um erro no sistema Efi");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res.toString());
    }
    @ExceptionHandler(UserAlreadyHavePaymentKeyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleUserAlreadyHavePaymentKeyException(UserAlreadyHavePaymentKeyException e){
        JSONObject res = new JSONObject();
        res.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res.toString());
    }
    @ExceptionHandler(UserDoesNotHavePaymentKey.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleUserDoesNotHavePaymentKey(UserDoesNotHavePaymentKey e){
        JSONObject res = new JSONObject();
        res.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res.toString());
    }

}
