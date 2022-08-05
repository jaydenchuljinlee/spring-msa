package com.bbrick.fund.comm.validation.annotation;

import com.bbrick.fund.comm.validation.validator.ProductIdFormatValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {ProductIdFormatValidator.class})
public @interface ProductIdFormat {
    String message() default "Product_id validation error";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean nullable() default false;
}
