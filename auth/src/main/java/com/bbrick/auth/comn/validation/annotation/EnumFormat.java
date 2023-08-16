package com.bbrick.auth.comn.validation.annotation;

import com.bbrick.auth.comn.validation.validator.EnumValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {EnumValidator.class})
public @interface EnumFormat {
    String message() default "Enum validation error";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean nullable() default false;

    Class<? extends Enum> enumClass();
}
