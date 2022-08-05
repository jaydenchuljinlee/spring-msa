package com.bbrick.fund.core.product.repository;

import com.bbrick.fund.core.product.domain.Product;

public interface ProductRepository {
    Product save(Product product);
}
