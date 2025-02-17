package com.hsf302.trialproject.user.service;

import com.hsf302.trialproject.user.entity.RefreshToken;

public interface RefreshTokenService {
    void deleteByToken(String token);
    void saveRefreshToken(RefreshToken refreshToken);
    boolean validateRefreshToken(String token, Long userId);
}
