package com.hsf302.trialproject.service;

import org.springframework.stereotype.Service;

@Service
public interface RegistrationTokenService {
    void saveToken(String token, Long userId);
    void deleteToken(String token);
    Long getUserIdByToken(String token);
}
