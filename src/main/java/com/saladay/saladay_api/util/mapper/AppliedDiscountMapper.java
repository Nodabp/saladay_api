package com.saladay.saladay_api.util.mapper;

import com.saladay.saladay_api.domain.orders.AppliedDiscount;
import com.saladay.saladay_api.dto.ordersDTO.AppliedDiscountDTO;
import org.springframework.stereotype.Component;

@Component
public class AppliedDiscountMapper {

    public AppliedDiscountDTO toDto(AppliedDiscount entity) {
        return AppliedDiscountDTO.builder()
                .discountId(entity.getDiscount().getId())
                .note(entity.getDiscount().getDescription()) // name 대신 description 사용
                .amount(entity.getAmount())
                .build();
    }
}
