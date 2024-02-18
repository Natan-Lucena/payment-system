package com.Zer0Rx.paymentsystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Zer0Rx.paymentsystem.dtos.UserResponse;
import com.Zer0Rx.paymentsystem.entities.User;
import com.Zer0Rx.paymentsystem.repositories.UserRepository;
import com.Zer0Rx.paymentsystem.utils.RandomString;

@Service()
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public UserResponse registerUser(User user){
        if(userRepository.findByEmail(user.getEmail()) != null){
            throw new RuntimeException("This email is already in use");
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        String randomCode = RandomString.generateRandomString(64);
        user.setVerificationCode(randomCode);
        user.setEnable(false);

        User savedUser = userRepository.save(user);

        UserResponse userResponse = new UserResponse(savedUser.getId() ,savedUser.getName(), savedUser.getEmail(), savedUser.getPassword());
        return userResponse;
    } 
}
