package com.Zer0Rx.paymentsystem.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.Zer0Rx.paymentsystem.dtos.UserRequest;
import com.Zer0Rx.paymentsystem.dtos.UserResponse;
import com.Zer0Rx.paymentsystem.entities.User;
import com.Zer0Rx.paymentsystem.services.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/user")

public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> registerUser(@RequestBody @Valid UserRequest userRequest){
        User user = userRequest.toModel();
        UserResponse userSaved = this.userService.registerUser(user);
        return ResponseEntity.ok().body(userSaved);
    }
}
