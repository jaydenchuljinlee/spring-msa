package com.bbrick.fund.comm.validation.validator;

import com.bbrick.fund.comm.validation.annotation.AddressFormat;
import com.bbrick.fund.comm.validation.checker.AddressFormatChecker;
import com.bbrick.fund.core.product.domain.Address;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AddressFormatValidator implements ConstraintValidator<AddressFormat, Address> {
    private boolean nullable = false;

    @Override
    public void initialize(AddressFormat constraintAnnotation) {
        this.nullable = constraintAnnotation.nullable();
    }

    @Override
    public boolean isValid(Address value, ConstraintValidatorContext context) {
        if (nullable) {
            return true;
        }

        return AddressFormatChecker.check(value);
    }
}
