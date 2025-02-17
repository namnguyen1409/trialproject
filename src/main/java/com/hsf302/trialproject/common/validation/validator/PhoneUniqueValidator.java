package com.hsf302.trialproject.common.validation.validator;

import com.hsf302.trialproject.common.validation.annotation.PhoneUnique;
import com.hsf302.trialproject.user.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PhoneUniqueValidator implements ConstraintValidator<PhoneUnique, String> {

    private UserRepository userRepo;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean isExist = userRepo.existsByPhone(value);
        return !isExist;
    }
}
