package com.saladay.saladay_api.domain.discount;

import com.saladay.saladay_api.domain.enums.DiscountTargetType;
import com.saladay.saladay_api.domain.enums.DiscountType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DiscountType type;

    private int value; // % 또는 금액

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private boolean isActive;

    @Enumerated(EnumType.STRING)
    private DiscountTargetType targetType; // MENU, CATEGORY, ALL

    private Long targetMenuId;     // 특정 메뉴 타겟, null일 경우 전체 할인, 값이 있으면 특정 대상만 적용
    private Long targetCategoryId; // 특정 카테고리 타겟

    private String description; // 예: '여름 한정 할인'

    private boolean stackable; // 중복 적용 가능 여부
    private int priority; // 낮을수록 우선 적용

}

