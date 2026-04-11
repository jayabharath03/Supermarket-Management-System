package com.supermarket.service.impl;

import com.supermarket.domain.Bill;
import com.supermarket.domain.BillItem;
import com.supermarket.domain.Product;
import com.supermarket.dto.BillResponse;
import com.supermarket.dto.BillingCreateRequest;
import com.supermarket.dto.BillingItemRequest;
import com.supermarket.exception.BadRequestException;
import com.supermarket.exception.ResourceNotFoundException;
import com.supermarket.repository.BillRepository;
import com.supermarket.repository.ProductRepository;
import com.supermarket.service.BillingService;
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
public class BillingServiceImpl implements BillingService {

    private final BillRepository billRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public BillResponse createBill(BillingCreateRequest request) {
        Bill bill = Bill.builder().billDate(LocalDateTime.now()).totalAmount(BigDecimal.ZERO).build();
        List<BillItem> billItems = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (BillingItemRequest itemRequest : request.items()) {
            Product product = productRepository.findById(itemRequest.productId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + itemRequest.productId()));

            if (product.getStockQuantity() < itemRequest.quantity()) {
                throw new BadRequestException("Insufficient stock for product: " + product.getName());
            }

            BigDecimal lineTotal = product.getPrice().multiply(BigDecimal.valueOf(itemRequest.quantity()));
            total = total.add(lineTotal);

            product.setStockQuantity(product.getStockQuantity() - itemRequest.quantity());

            BillItem billItem = BillItem.builder()
                    .bill(bill)
                    .product(product)
                    .quantity(itemRequest.quantity())
                    .lineTotal(lineTotal)
                    .build();
            billItems.add(billItem);
        }

        bill.setItems(billItems);
        bill.setTotalAmount(total);
        Bill savedBill = billRepository.save(bill);

        log.info("Created bill {} with {} item(s), total {}", savedBill.getId(), savedBill.getItems().size(), savedBill.getTotalAmount());

        List<BillResponse.BillLineResponse> lines = savedBill.getItems().stream()
                .map(i -> new BillResponse.BillLineResponse(i.getProduct().getName(), i.getQuantity(), i.getLineTotal()))
                .toList();

        return new BillResponse(savedBill.getId(), savedBill.getTotalAmount(), savedBill.getBillDate(), lines);
    }
}
