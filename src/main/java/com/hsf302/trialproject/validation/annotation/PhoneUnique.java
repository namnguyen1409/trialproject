package com.hsf302.trialproject.validation.annotation;

import com.hsf302.trialproject.validation.validator.PhoneUniqueValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PhoneUniqueValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneUnique {
    String message() default "Số điện thoại đã tồn tại";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};
}
