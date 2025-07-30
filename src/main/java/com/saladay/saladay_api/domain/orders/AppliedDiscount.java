package com.saladay.saladay_api.domain.orders;

import com.saladay.saladay_api.domain.discount.Discount;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

