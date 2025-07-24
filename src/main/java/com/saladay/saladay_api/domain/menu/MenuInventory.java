package com.saladay.saladay_api.domain.menu;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class MenuInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private int stockQuantity;

    private boolean soldOutManually; // 관리자에 의해 수동 품절

    private boolean partialSoldOut;  // 구성 요소 일부 품절

    private int thresholdAlert; // 품절 경고 알림 기준
}

