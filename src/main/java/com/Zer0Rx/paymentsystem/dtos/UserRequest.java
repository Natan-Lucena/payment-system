package com.Zer0Rx.paymentsystem.dtos;

import com.Zer0Rx.paymentsystem.entities.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequest(
    @NotNull(message = "The name cant be null") 
    @NotBlank(message = "The name cant be blank") 
    @Size(min = 8, message = "The name must have at least 8 characthers")
    String name, 
    @NotNull(message = "The email cant be null") 
    @NotBlank(message = "The email cant be blank")
    @Email(message = "Must be a valid email") 
    String email, 
    @NotNull(message = "The password cant be null") 
    @NotBlank(message = "The password cant be blank") 
    @Size(min = 8, message = "The password must have at least 8 characthers")
    String password
    ) {
    public User toModel(){
        return new User(name, email, password);
    }
}
