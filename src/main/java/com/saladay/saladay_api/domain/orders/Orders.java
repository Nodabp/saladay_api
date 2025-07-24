package com.saladay.saladay_api.domain.orders;

import com.saladay.saladay_api.domain.common.BaseTimeEntity;
import com.saladay.saladay_api.domain.users.Users;
import com.saladay.saladay_api.domain.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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


    // 토스 관련 컬럼 추가

    private String paymentKey;         // Toss 결제 키 저장
    private String orderId;            // 외부 결제용 주문 ID
    private String customerName;       // 구매자 이름
    private String customerMobile;     // 구매자 연락처
    private String customerEmail;

    private LocalDateTime paidAt;       // 결제 완료 시점
    private LocalDateTime canceledAt;   // 주문 취소 시점

}
