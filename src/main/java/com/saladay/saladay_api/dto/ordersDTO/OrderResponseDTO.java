package com.saladay.saladay_api.dto.ordersDTO;


import com.saladay.saladay_api.dto.priceDTO.PriceDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    private String orderId;
    private String orderStatus;
    private int totalPrice;
    private String paymentKey;
    private List<PriceDetailDTO> orderDetails; // 주문에 포함된 메뉴 상세
}
