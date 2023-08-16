package com.bbrick.auth.comn.validation.annotation;

import com.bbrick.auth.comn.validation.validator.UserPasswordFormatValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {UserPasswordFormatValidator.class})
public @interface UserPasswordFormat {
    String message() default "UserPassword validation error";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean nullable() default false;
}
