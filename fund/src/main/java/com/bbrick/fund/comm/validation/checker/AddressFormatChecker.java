package com.bbrick.fund.comm.validation.checker;

import com.bbrick.fund.core.product.domain.Address;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressFormatChecker {
    public static boolean check(Address address) {
        return StringUtils.isNotBlank(address.getAddress1()) && StringUtils.isNotBlank(address.getZipCode());
    }
}
