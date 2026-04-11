package com.supermarket.service.impl;

import com.supermarket.domain.Order;
import com.supermarket.domain.OrderItem;
import com.supermarket.domain.Product;
import com.supermarket.dto.OrderCreateRequest;
import com.supermarket.dto.OrderResponse;
import com.supermarket.exception.ResourceNotFoundException;
import com.supermarket.repository.OrderRepository;
import com.supermarket.repository.ProductRepository;
import com.supermarket.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public OrderResponse create(OrderCreateRequest request) {
        Order order = Order.builder().status("CREATED").createdAt(LocalDateTime.now()).totalAmount(BigDecimal.ZERO).build();
        List<OrderItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (var item : request.items()) {
            Product product = productRepository.findById(item.productId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + item.productId()));
            BigDecimal lineTotal = product.getPrice().multiply(BigDecimal.valueOf(item.quantity()));
            total = total.add(lineTotal);
            items.add(OrderItem.builder().order(order).product(product).quantity(item.quantity()).lineTotal(lineTotal).build());
        }

        order.setItems(items);
        order.setTotalAmount(total);
        Order saved = orderRepository.save(order);
        log.info("Created order {}", saved.getId());
        return new OrderResponse(saved.getId(), saved.getStatus(), saved.getTotalAmount(), saved.getCreatedAt());
    }

    @Override
    public List<OrderResponse> getAll() {
        return orderRepository.findAll().stream()
                .map(order -> new OrderResponse(order.getId(), order.getStatus(), order.getTotalAmount(), order.getCreatedAt()))
                .toList();
    }
}
