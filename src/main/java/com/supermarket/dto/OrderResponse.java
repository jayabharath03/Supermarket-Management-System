package com.supermarket.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderResponse(Long orderId, String status, BigDecimal totalAmount, LocalDateTime createdAt) {
}
