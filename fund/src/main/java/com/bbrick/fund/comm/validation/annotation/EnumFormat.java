package com.bbrick.fund.comm.validation.annotation;

import com.bbrick.fund.comm.validation.validator.EnumFormatValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = EnumFormatValidator.class)
public @interface EnumFormat {
    String message() default "Enum validation error";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean nullable() default false;

    Class<? extends Enum> enumClass();
}
