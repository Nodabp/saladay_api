package com.saladay.saladay_api.dto.ordersDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppliedDiscountDTO {
    private Long discountId;
    private int amount;
    private String note;
}
