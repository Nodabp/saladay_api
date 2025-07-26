package com.saladay.saladay_api.repository;

import com.saladay.saladay_api.domain.orders.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
