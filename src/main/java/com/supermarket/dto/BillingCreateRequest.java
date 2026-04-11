package com.supermarket.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record BillingCreateRequest(@NotEmpty List<BillingItemRequest> items) {
}
