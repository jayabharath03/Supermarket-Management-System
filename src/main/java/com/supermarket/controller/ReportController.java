package com.supermarket.controller;

import com.supermarket.dto.SalesReportResponse;
import com.supermarket.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/sales")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<SalesReportResponse> salesReport() {
        return ResponseEntity.ok(reportService.getSalesReport());
    }
}
