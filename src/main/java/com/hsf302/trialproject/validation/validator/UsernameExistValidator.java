package com.hsf302.trialproject.validation.validator;

import com.hsf302.trialproject.validation.annotation.UsernameExist;
import com.hsf302.trialproject.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class UsernameExistValidator implements ConstraintValidator<UsernameExist, String> {
    private final UserService userService;

    public UsernameExistValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return userService.findUserByUsername(username) != null;
    }
}
