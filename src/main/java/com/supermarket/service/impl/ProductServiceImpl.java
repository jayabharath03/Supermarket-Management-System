package com.supermarket.service.impl;

import com.supermarket.domain.Product;
import com.supermarket.dto.ProductRequest;
import com.supermarket.dto.ProductResponse;
import com.supermarket.repository.ProductRepository;
import com.supermarket.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<ProductResponse> getAll() {
        log.debug("Fetching all products");
        return productRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional
    public ProductResponse create(ProductRequest request) {
        Product saved = productRepository.save(Product.builder()
                .sku(request.sku())
                .name(request.name())
                .price(request.price())
                .stockQuantity(request.stockQuantity())
                .build());
        log.info("Created product {} with id {}", saved.getName(), saved.getId());
        return toResponse(saved);
    }

    private ProductResponse toResponse(Product product) {
        return new ProductResponse(product.getId(), product.getSku(), product.getName(), product.getPrice(), product.getStockQuantity());
    }
}
