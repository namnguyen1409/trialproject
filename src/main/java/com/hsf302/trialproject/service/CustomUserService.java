package com.hsf302.trialproject.service;

import com.hsf302.trialproject.security.CustomUserDetails;
import com.hsf302.trialproject.enums.MessageKeyEnum;
import com.hsf302.trialproject.entity.User;
import com.hsf302.trialproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomUserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final MessageService messageService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         Optional<User> user = userRepository.findByUsername(username);
        return new CustomUserDetails(user.get());
    }
}
