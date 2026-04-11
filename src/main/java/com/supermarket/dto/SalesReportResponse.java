package com.supermarket.dto;

import java.math.BigDecimal;

public record SalesReportResponse(Long totalBills, BigDecimal grossSales) {
}
