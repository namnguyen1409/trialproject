package com.hsf302.trialproject.common.config;

import com.hsf302.trialproject.auth.security.CustomUserDetails;
import com.hsf302.trialproject.user.entity.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<User> {

    @NotNull
    @Override
    public Optional<User> getCurrentAuditor() {
        if (SecurityContextHolder.getContext().getAuthentication() == null
                || SecurityContextHolder.getContext().getAuthentication().getPrincipal() == "anonymousUser") {
            return Optional.empty();
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomUserDetails customUserDetails) {
            return Optional.of(customUserDetails.getUser());
        }
        return Optional.empty();
    }
}
