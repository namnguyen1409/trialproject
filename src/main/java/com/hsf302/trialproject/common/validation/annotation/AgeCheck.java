package com.hsf302.trialproject.common.validation.annotation;

import com.hsf302.trialproject.common.validation.validator.AgeCheckValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AgeCheckValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface AgeCheck {
    String message() default "Không đủ tuổi";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};
    int min() default -1;
    int max() default -1;
}
