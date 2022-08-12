package com.bbrick.trade.comm.validation.validator;

import com.bbrick.trade.comm.validation.annotation.EnumFormat;
import com.bbrick.trade.comm.validation.checker.EnumFormatChecker;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumFormatValidator implements ConstraintValidator<EnumFormat, String> {
    private boolean nullable = false;
    private Class<? extends Enum> enumClass;

    @Override
    public void initialize(EnumFormat constraintAnnotation) {
        this.nullable = constraintAnnotation.nullable();
        this.enumClass = constraintAnnotation.enumClass();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (nullable) {
            return true;
        }

        return EnumFormatChecker.check(enumClass, value);
    }
}
