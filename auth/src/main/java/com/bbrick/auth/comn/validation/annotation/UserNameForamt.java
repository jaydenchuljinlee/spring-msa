package com.bbrick.auth.comn.validation.annotation;

import com.bbrick.auth.comn.validation.validator.UserNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {UserNameValidator.class})
public @interface UserNameForamt {
    String message() default "UserName validation error";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean nullable() default false;
}
