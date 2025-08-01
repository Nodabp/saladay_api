package com.saladay.saladay_api.repository;

import com.saladay.saladay_api.domain.orders.OrdersItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersItemRepository extends JpaRepository<OrdersItem, Long> {
    List<OrdersItem> findAllByMenuId(Long menuId);
    List<OrdersItem> findAllByOrdersId(Long ordersId);
}
