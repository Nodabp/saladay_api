package com.saladay.saladay_api.dto.ordersDTO;

import com.saladay.saladay_api.domain.enums.OptionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemOptionResponseDTO {

    private Long menuOptionId;
    private String name;
    private int extraPrice;   // 옵션 자체 가격
    private int priceImpact; // 실제 반영된 가격 영향
    private OptionType type;
}
