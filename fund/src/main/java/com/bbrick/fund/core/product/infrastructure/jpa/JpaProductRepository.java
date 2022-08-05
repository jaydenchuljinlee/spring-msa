package com.bbrick.fund.core.product.infrastructure.jpa;

import com.bbrick.fund.core.product.domain.Product;
import com.bbrick.fund.core.product.domain.exceptions.ProductRepositoryIntegrationException;
import com.bbrick.fund.core.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.function.Supplier;

interface InnerProductRepository extends JpaRepository<Product, Long> {
}

@Repository
@RequiredArgsConstructor
public class JpaProductRepository implements ProductRepository {
    private final InnerProductRepository repository;

    @Override
    public Product save(Product product) {
        return this.wrapIntegrationException(
                () -> this.repository.save(product)
        );
    }

    private <T> T wrapIntegrationException(Supplier<T> process) {
        try {
            return process.get();
        } catch(Exception e) {
            throw new ProductRepositoryIntegrationException(e);
        }

    }
}
