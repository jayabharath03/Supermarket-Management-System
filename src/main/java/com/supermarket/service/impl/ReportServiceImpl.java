package com.supermarket.service.impl;

import com.supermarket.dto.SalesReportResponse;
import com.supermarket.repository.BillRepository;
import com.supermarket.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {

    private final BillRepository billRepository;

    @Override
    public SalesReportResponse getSalesReport() {
        var bills = billRepository.findAll();
        BigDecimal gross = bills.stream().map(b -> b.getTotalAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
        log.info("Generated sales report for {} bills", bills.size());
        return new SalesReportResponse((long) bills.size(), gross);
    }
}
