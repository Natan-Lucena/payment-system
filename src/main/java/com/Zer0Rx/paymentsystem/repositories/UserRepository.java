package com.Zer0Rx.paymentsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Zer0Rx.paymentsystem.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByVerificationCode(String verifationCode);
    User findByName(String name);
}
