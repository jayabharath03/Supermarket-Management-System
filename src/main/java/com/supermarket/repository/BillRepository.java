package com.supermarket.repository;

import com.supermarket.domain.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface BillRepository extends JpaRepository<Bill, Long> {

    @Query("select coalesce(sum(b.totalAmount), 0) from Bill b")
    BigDecimal calculateGrossSales();
}
