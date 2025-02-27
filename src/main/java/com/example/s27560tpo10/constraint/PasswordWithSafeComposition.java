package com.example.s27560tpo10.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordWithSafeComposition {
    String message() default "{com.example.s27560tpo10.InvalidPassword.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
