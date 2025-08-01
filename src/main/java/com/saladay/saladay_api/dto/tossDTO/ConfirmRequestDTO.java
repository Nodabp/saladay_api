package com.saladay.saladay_api.dto.tossDTO;

import com.saladay.saladay_api.domain.enums.OrderStatus;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmRequestDTO {

    private String orderId;
    private int amount;
    private String paymentKey;
    private OrderStatus orderStatus;
}
