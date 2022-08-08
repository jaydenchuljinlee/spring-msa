package com.bbrick.fund.core.product.repository;

import com.bbrick.fund.core.product.domain.Product;

import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findById(long productId);
    Product save(Product product);
}
