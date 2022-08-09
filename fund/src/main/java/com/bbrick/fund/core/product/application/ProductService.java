package com.bbrick.fund.core.product.application;

import com.bbrick.fund.core.product.domain.Product;
import com.bbrick.fund.core.product.domain.dto.ProductRequest;
import com.bbrick.fund.core.product.infrastructure.jpa.JpaProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class ProductService {
    private final JpaProductRepository jpaProductRepository;

    public Product getProductById(long productId) {
        Optional<Product> optional = jpaProductRepository.findById(productId);

        if (optional.isEmpty()) {
            // TODO product not founded
        }

        return optional.get();

    }

    public Product registProduct(ProductRequest productRequest) {

        Product converted = Product.convertFromProductRequest(productRequest);

        Product result = this.jpaProductRepository.save(converted);

        return result;
    }
}
