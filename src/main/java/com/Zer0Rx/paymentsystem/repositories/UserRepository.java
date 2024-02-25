package com.Zer0Rx.paymentsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.Zer0Rx.paymentsystem.entities.User;;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByEmail(String email);
    User findByVerificationCode(String verifationCode);
}
