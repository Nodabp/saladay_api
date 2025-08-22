package com.saladay.saladay_api.dto.ordersDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {
    private Long userId;                   // 회원 ID (비회원이면 null)
    @Builder.Default
    private String customerName = "비회원";
    @Builder.Default
    private String customerMobile = "01000000000";
    @Builder.Default
    private String customerEmail = "1@1.com";
    private int pointAmount;               // 사용 포인트
    private List<OrderItemRequestDTO> items;  // 주문 아이템 목록
}
