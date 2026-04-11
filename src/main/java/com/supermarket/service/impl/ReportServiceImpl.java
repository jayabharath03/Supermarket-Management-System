package com.supermarket.service.impl;

import com.supermarket.dto.SalesReportResponse;
import com.supermarket.repository.BillRepository;
import com.supermarket.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {

    private final BillRepository billRepository;

    @Override
    public SalesReportResponse getSalesReport() {
        long totalBills = billRepository.count();
        var gross = billRepository.calculateGrossSales();
        log.info("Generated sales report for {} bills", totalBills);
        return new SalesReportResponse(totalBills, gross);
    }
}
