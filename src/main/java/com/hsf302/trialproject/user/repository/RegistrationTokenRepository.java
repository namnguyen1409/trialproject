package com.hsf302.trialproject.user.repository;

import com.hsf302.trialproject.user.entity.RegistrationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationTokenRepository extends JpaRepository<RegistrationToken, Long> {
    RegistrationToken findByToken(String token);
    RegistrationToken findByEmail(String email);
    void deleteByToken(String token);
}
