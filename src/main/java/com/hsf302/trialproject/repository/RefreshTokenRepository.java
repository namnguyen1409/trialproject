package com.hsf302.trialproject.repository;

import com.hsf302.trialproject.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    void deleteByToken(String token);
    RefreshToken findByToken(String token);
    RefreshToken findByTokenAndUserId(String token, Long userId);
}
