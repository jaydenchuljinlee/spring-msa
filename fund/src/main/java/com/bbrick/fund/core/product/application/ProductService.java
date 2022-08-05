package com.bbrick.fund.core.product.application;

import com.bbrick.fund.core.product.domain.Product;
import com.bbrick.fund.core.product.domain.dto.ProductRequest;
import com.bbrick.fund.core.product.infrastructure.jpa.JpaProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {
    private final JpaProductRepository jpaProductRepository;

    public Product registProduct(ProductRequest productRequest) {

        Product converted = Product.convertFromProductRequest(productRequest);

        Product result = this.jpaProductRepository.save(converted);

        return result;
    }
}
