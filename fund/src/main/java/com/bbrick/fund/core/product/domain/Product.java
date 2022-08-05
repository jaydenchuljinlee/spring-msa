package com.bbrick.fund.core.product.domain;

import com.bbrick.fund.comm.entity.BaseEntity;
import com.bbrick.fund.core.product.domain.dto.ProductRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table @Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) @Getter
public class Product extends BaseEntity {
    @Column(name = "product_name")
    private String productName;

    @Embedded
    private Address address;

    @Column(name = "amount")
    private int amount;

    @Builder
    protected Product(String productName, Address address, int amount) {
        this.productName = productName;
        this.address = address;
        this.amount = amount;
    }

    public static Product convertFromProductRequest(ProductRequest productRequest) {
        return new ProductBuilder()
                .productName(productRequest.getProductName())
                .address(productRequest.getAddress())
                .amount(productRequest.getAmount())
                .build();
    }
}
