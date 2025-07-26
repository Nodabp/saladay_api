package com.saladay.saladay_api.repository;

import com.saladay.saladay_api.domain.orders.OrdersItemOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersItemOptionRepository extends JpaRepository<OrdersItemOption, Long> {
}
