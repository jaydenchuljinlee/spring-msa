package com.bbrick.fund.core.product.domain.dto;

import com.bbrick.fund.comm.validation.annotation.AddressFormat;
import com.bbrick.fund.comm.validation.annotation.ProductIdFormat;
import com.bbrick.fund.core.product.domain.Address;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class ProductRequest {
    @NotBlank
    @ProductIdFormat
    private String productCode;

    @NotBlank
    private String productName;

    @AddressFormat
    private Address address;

    @NotBlank
    private int amount;
}
