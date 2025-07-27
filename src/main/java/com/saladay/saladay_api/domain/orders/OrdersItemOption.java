package com.saladay.saladay_api.domain.orders;

import com.saladay.saladay_api.domain.menu.MenuOption;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrdersItemOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "orders_item_id")
    private OrdersItem ordersItem;

    @ManyToOne
    @JoinColumn(name = "menu_option_id")
    private MenuOption menuOption;

    private int priceImpact; // 이 주문에서 이 옵션이 더한 금액
}

