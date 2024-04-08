package com.Zer0Rx.paymentsystem.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.Zer0Rx.paymentsystem.dtos.UserRequest;
import com.Zer0Rx.paymentsystem.dtos.UserResponse;
import com.Zer0Rx.paymentsystem.entities.User;
import com.Zer0Rx.paymentsystem.services.UserService;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/user")

public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody @Valid UserRequest userRequest) throws UnsupportedEncodingException, MessagingException{
        User user = userRequest.toModel();
        UserResponse userSaved = this.userService.registerUser(user);
        return ResponseEntity.ok().body(userSaved);
    }
    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        if(userService.verify(code)){
            return "verify_sucecces";
        }
        return "verify_failed";
    }
    @GetMapping("/teste")
    public String teste(){
        return "Logado";
    }
}
