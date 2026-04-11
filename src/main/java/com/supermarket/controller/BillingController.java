package com.supermarket.controller;

import com.supermarket.dto.BillResponse;
import com.supermarket.dto.BillingCreateRequest;
import com.supermarket.service.BillingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/billing")
@RequiredArgsConstructor
public class BillingController {

    private final BillingService billingService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN','CASHIER')")
    public ResponseEntity<BillResponse> createBill(@Valid @RequestBody BillingCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(billingService.createBill(request));
    }
}
