package com.saladay.saladay_api.domain.entitiy;

import com.saladay.saladay_api.domain.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users users;

    private int totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문완료, 처리중, 취소 등

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private List<OrdersItem> items = new ArrayList<>();
}
