package com.bbrick.fund.core.product.domain;

import com.bbrick.fund.comm.entity.BaseEntity;
import lombok.AccessLevel;
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
}
