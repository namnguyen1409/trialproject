package com.hsf302.trialproject.auth.service;

import com.hsf302.trialproject.auth.security.CustomUserDetails;
import com.hsf302.trialproject.common.enums.MessageKeyEnum;
import com.hsf302.trialproject.common.service.MessageService;
import com.hsf302.trialproject.user.entity.User;
import com.hsf302.trialproject.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final MessageService messageService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(messageService.getMessage(MessageKeyEnum.ERROR_USERNAME_NOT_FOUND.getKey()));
        }
        return new CustomUserDetails(user.get());
    }

    public UserDetails loadUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(messageService.getMessage(MessageKeyEnum.ERROR_USERNAME_NOT_FOUND.getKey()));
        }
        return new CustomUserDetails(user.get());
    }


    public CustomUserDetails loadUserByRefreshToken(String refreshToken) {
        Optional<User> user = userRepository.findByRefreshTokens_Token(refreshToken);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(messageService.getMessage(MessageKeyEnum.ERROR_USERNAME_NOT_FOUND.getKey()));
        }
        return new CustomUserDetails(user.get());
    }


}
