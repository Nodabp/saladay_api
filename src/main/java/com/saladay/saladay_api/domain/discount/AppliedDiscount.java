package com.saladay.saladay_api.domain.discount;

import com.saladay.saladay_api.domain.orders.OrdersItem;
import jakarta.persistence.*;

@Entity

public class AppliedDiscount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Discount discount;

    private int amount;  // 이 항목에 적용된 할인액

    private String appliedNote; // 설명 or 정책명

    @ManyToOne
    @JoinColumn(name = "orders_item_id")
    private OrdersItem ordersItem;
}

