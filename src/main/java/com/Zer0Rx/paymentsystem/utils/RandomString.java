package com.Zer0Rx.paymentsystem.utils;

import java.security.SecureRandom;

public class RandomString {
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String generateRandomString(int length){
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < length; i++){
            int index = secureRandom.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }
}
