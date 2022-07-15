package com.bbrick.auth.core.user.domain.exceptions;

import com.bbrick.auth.comn.exceptions.DomainValueException;

public class UserDomainValueException extends DomainValueException {
    public UserDomainValueException(String fieldName, Object value) {
        super("User domain field '" + fieldName + "' doesn't support given value: " + value);
    }
}
