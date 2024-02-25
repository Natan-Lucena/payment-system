package com.Zer0Rx.paymentsystem.services;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Zer0Rx.paymentsystem.dtos.UserResponse;
import com.Zer0Rx.paymentsystem.entities.User;
import com.Zer0Rx.paymentsystem.repositories.UserRepository;
import com.Zer0Rx.paymentsystem.utils.RandomString;

import jakarta.mail.MessagingException;

@Service()
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MailService mailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public UserResponse registerUser(User user) throws UnsupportedEncodingException, MessagingException{
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
        mailService.sendVerificationEmail(user);
        return userResponse;
    } 


    public boolean verify(String verificationCode){
        User user = userRepository.findByVerificationCode(verificationCode);
        if(user == null || user.isEnabled()){
            return false;
        }
        user.setVerificationCode(null);
        user.setEnable(true);
        userRepository.save(user);
        return true;
    }
}
