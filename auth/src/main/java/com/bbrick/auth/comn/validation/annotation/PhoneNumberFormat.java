package com.bbrick.auth.comn.validation.annotation;

import com.bbrick.auth.comn.validation.validator.PhoneNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {PhoneNumberValidator.class})
public @interface PhoneNumberFormat {
    String message() default "PhoneNumber validation error";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean nullable() default false;
}
