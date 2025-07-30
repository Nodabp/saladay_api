package com.saladay.saladay_api.repository;

import com.saladay.saladay_api.domain.orders.AppliedDiscount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppliedDiscountRepository extends JpaRepository<AppliedDiscount, Long> {
}
