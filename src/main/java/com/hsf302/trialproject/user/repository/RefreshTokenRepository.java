package com.hsf302.trialproject.user.repository;

import com.hsf302.trialproject.user.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    void deleteByToken(String token);
    RefreshToken findByToken(String token);
    RefreshToken findByTokenAndUserId(String token, Long userId);
}
