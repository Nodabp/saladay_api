package com.saladay.saladay_api.repository;

import com.saladay.saladay_api.domain.orders.OrdersItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersItemRepository extends JpaRepository<OrdersItem, Long> {
}
