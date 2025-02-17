package com.hsf302.trialproject.user.service.Impl;

import com.hsf302.trialproject.user.entity.RefreshToken;
import com.hsf302.trialproject.user.mapper.RefreshTokenMapper;
import com.hsf302.trialproject.user.repository.RefreshTokenRepository;
import com.hsf302.trialproject.user.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenMapper refreshTokenMapper;

    @Override
    public void deleteByToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }

    @Override
    public void saveRefreshToken(RefreshToken refreshToken) {
        refreshTokenRepository.save(refreshToken);
    }

    @Override
    public boolean validateRefreshToken(String token, Long userId) {
        return refreshTokenRepository.findByTokenAndUserId(token, userId) != null;
    }


}
