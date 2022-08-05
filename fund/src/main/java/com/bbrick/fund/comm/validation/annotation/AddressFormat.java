package com.bbrick.fund.comm.validation.annotation;

import com.bbrick.fund.comm.validation.validator.AddressFormatValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {AddressFormatValidator.class})
public @interface AddressFormat {
    String message() default "Address validation error";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean nullable() default false;
}
