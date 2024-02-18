package com.Zer0Rx.paymentsystem.dtos;

import com.Zer0Rx.paymentsystem.entities.User;

public record UserRequest(String name, String email, String password) {
    public User toModel(){
        return new User(name, email, password);
    }
}
