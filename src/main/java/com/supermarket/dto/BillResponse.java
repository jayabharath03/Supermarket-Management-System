package com.supermarket.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record BillResponse(Long billId, BigDecimal totalAmount, LocalDateTime billDate, List<BillLineResponse> items) {
    public record BillLineResponse(String productName, Integer quantity, BigDecimal lineTotal) {
    }
}
