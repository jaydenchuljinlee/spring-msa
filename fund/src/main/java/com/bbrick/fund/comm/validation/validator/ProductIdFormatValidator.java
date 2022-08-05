package com.bbrick.fund.comm.validation.validator;

import com.bbrick.fund.comm.validation.annotation.ProductIdFormat;
import com.bbrick.fund.comm.validation.checker.ProductIdFormatChecker;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ProductIdFormatValidator implements ConstraintValidator<ProductIdFormat, String> {
    private boolean nullable = false;

    @Override
    public void initialize(ProductIdFormat constraintAnnotation) {
        this.nullable = constraintAnnotation.nullable();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (nullable) {
            return true;
        }

        return ProductIdFormatChecker.check(value);
    }
}
