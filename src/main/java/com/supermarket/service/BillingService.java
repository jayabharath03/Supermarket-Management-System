package com.supermarket.service;

import com.supermarket.dto.BillResponse;
import com.supermarket.dto.BillingCreateRequest;

public interface BillingService {
    BillResponse createBill(BillingCreateRequest request);
}
