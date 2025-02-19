package com.hsf302.trialproject.repository;

import com.hsf302.trialproject.entity.RegistrationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationTokenRepository extends JpaRepository<RegistrationToken, Long> {
    RegistrationToken findByToken(String token);
    RegistrationToken findByEmail(String email);
    void deleteByToken(String token);
}
