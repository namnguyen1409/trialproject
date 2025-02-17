package com.hsf302.trialproject.common.validation.validator;

import com.hsf302.trialproject.common.validation.annotation.EmailUnique;
import com.hsf302.trialproject.user.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EmailUniqueValidator implements ConstraintValidator<EmailUnique, String> {

    private UserRepository userRepo;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean isExist = userRepo.existsByEmail(value);
        return !isExist;
    }
}
