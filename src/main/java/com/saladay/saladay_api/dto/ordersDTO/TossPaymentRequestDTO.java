package com.saladay.saladay_api.dto.ordersDTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TossPaymentRequestDTO {
    /* 토스 결제용 추후 보완 필요.*/
    private String orderId;        // 내부 주문 기준
    private int amount;            // PriceDetailDTO.finalPrice
    private String orderName;      // PriceDetailDTO.name
    private String customerMobile; // 익명 사용자면 간단 처리
    private String successUrl;
    private String failUrl;

}
