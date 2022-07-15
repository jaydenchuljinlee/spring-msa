package com.bbrick.auth.comn.validation.validator;

import com.bbrick.auth.comn.validation.annotation.PhoneNumberFormat;
import com.bbrick.auth.comn.validation.checker.PhoneNumberFormatChecker;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberFormat, String> {
    private boolean nullable = false;


    @Override
    public void initialize(PhoneNumberFormat constraintAnnotation) {
        this.nullable = constraintAnnotation.nullable();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (nullable) {
            return true;
        }

        return PhoneNumberFormatChecker.check(value);
    }
}
