package com.hsf302.trialproject.validation.annotation;

import com.hsf302.trialproject.validation.validator.UsernameExistValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UsernameExistValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameExist {
    String message() default "Tên đăng nhập không tồn tại";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}