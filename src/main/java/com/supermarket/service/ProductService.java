package com.supermarket.service;

import com.supermarket.dto.ProductRequest;
import com.supermarket.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getAll();
    ProductResponse create(ProductRequest request);
}
