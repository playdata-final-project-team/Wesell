package com.wesell.imageserver.controller.feign;

import com.wesell.imageserver.domain.repository.ProductImageViewDao;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v2")
public class ProductImageController {
    private final ProductImageViewDao productImageViewDao;
    public ProductImageController(ProductImageViewDao productImageViewDao) {
        this.productImageViewDao = productImageViewDao;
    }

    @GetMapping("images/{productId}/url")
    public ResponseEntity<String> getUrlByProductId(@PathVariable Long productId) {
        return ResponseEntity.ok(productImageViewDao.searchUrlByProductId(productId));
    }
}
