package com.bbrick.fund.view.web.product;

import com.bbrick.fund.comm.BaseResponse;
import com.bbrick.fund.comm.web.constants.ProductConstants;
import com.bbrick.fund.core.product.application.ProductService;
import com.bbrick.fund.core.product.domain.Product;
import com.bbrick.fund.core.product.domain.dto.ProductRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
public class ProductController {
    private final ProductService productService;

    @PostMapping(ProductConstants.URL.PRODUCT_REGISTERING_PATH)
    public ResponseEntity<BaseResponse<Product>> registProduct(ProductRequest product) {

        Product result = productService.registProduct(product);

        return ResponseEntity
                .ok()
                .body(BaseResponse.success());
    }
}
