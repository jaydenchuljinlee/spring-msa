package com.bbrick.fund.comm.validation.checker;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductIdFormatChecker {
    private static final String PRODUCT_ID_REGEX = "^([0-9]{2})([B|P])([0-9]{4})$";
    private static final Pattern PRODUCT_ID_PATTERN = Pattern.compile(PRODUCT_ID_REGEX);

    public static boolean check(String mayProductId) {
        if (StringUtils.isBlank(mayProductId)) {
            return false;
        }

        return StringUtils.isNotBlank(mayProductId) &&
                PRODUCT_ID_PATTERN.matcher(PRODUCT_ID_REGEX).matches();

    }
}
