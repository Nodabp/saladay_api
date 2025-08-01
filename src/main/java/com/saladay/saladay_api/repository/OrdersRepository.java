package com.saladay.saladay_api.repository;

import com.saladay.saladay_api.domain.orders.Orders;
import jakarta.persistence.criteria.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
    Orders findByOrderId(String orderId);
}
