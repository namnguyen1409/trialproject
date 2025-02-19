package com.hsf302.trialproject.service;

import com.hsf302.trialproject.entity.RefreshToken;

public interface RefreshTokenService {
    void deleteByToken(String token);
    void saveRefreshToken(RefreshToken refreshToken);
    boolean validateRefreshToken(String token, Long userId);
}
