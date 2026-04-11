package com.supermarket.service;

import com.supermarket.dto.OrderCreateRequest;
import com.supermarket.dto.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse create(OrderCreateRequest request);
    List<OrderResponse> getAll();
}
