package com.saladay.saladay_api.dto.ordersDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TossPaymentResponseDTO {
    private String paymentKey;
    private String orderId;
    private String orderName;
    private String customerEmail;
    private String customerMobilePhone;
    private String customerName;
    private int totalAmount;
    private TossPaymentStatus status; // Enum으로 정의
    private String method;            // 결제 수단: CARD 등
    private String receiptUrl;
    private LocalDateTime approvedAt;
}
enum TossPaymentStatus {
    READY,
    IN_PROGRESS,
    WAITING_FOR_DEPOSIT,
    DONE,
    CANCELED,
    PARTIAL_CANCELED,
    ABORTED,
    EXPIRED
}