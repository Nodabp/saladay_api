package com.saladay.saladay_api.domain.orders;

import com.saladay.saladay_api.domain.menu.Menu;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrdersItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "orders_id")
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private int quantity; // 묶을 수 있으면 >1, 옵션/할인이 다르면 1 고정

    private int originalPrice; // 메뉴 정가
    private int discountedPrice; // 최종 적용된 가격 (단일 항목당)

    private String discountSummary; // 이벤트 설명, 정책명 등

    private String optionSummary; // 선택된 옵션 간단 요약

    @OneToMany(mappedBy = "ordersItem", cascade = CascadeType.ALL)
    private List<AppliedDiscount> appliedDiscounts = new ArrayList<>();
}
