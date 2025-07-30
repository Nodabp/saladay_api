package com.saladay.saladay_api.util.converter;

import com.saladay.saladay_api.domain.orders.AppliedDiscount;
import com.saladay.saladay_api.dto.menuDTO.MenuOptionDTO;
import com.saladay.saladay_api.dto.ordersDTO.AppliedDiscountDTO;
import com.saladay.saladay_api.dto.ordersDTO.OrderItemOptionResponseDTO;
import com.saladay.saladay_api.dto.ordersDTO.OrderItemResponseDTO;
import com.saladay.saladay_api.dto.priceDTO.PriceDetailDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderItemConverter {

    public OrderItemResponseDTO from(PriceDetailDTO priceDetail, int quantity) {

        return OrderItemResponseDTO.builder()
                .menuId(priceDetail.getMenuId())
                .menuName(priceDetail.getName())
                .quantity(quantity)
                .originalPrice(priceDetail.getBasePrice())
                .discountedPrice(priceDetail.getDiscountedPrice())
                .discountSummary(
                        priceDetail.getAppliedDiscounts().stream()
                                .map(AppliedDiscountDTO::getNote)
                                .collect(Collectors.joining(", "))
                )
                .optionSummary( // 옵션 이름들 이어붙이기
                        priceDetail.getSelectedOptions().stream()
                                .map(MenuOptionDTO::getName)
                                .collect(Collectors.joining(", "))
                )
                .options( // 옵션을 OrderItemOptionResponseDTO로 변환
                        priceDetail.getSelectedOptions().stream()
                                .map(opt -> OrderItemOptionResponseDTO.builder()
                                        .menuOptionId(opt.getId())
                                        .name(opt.getName())
                                        .extraPrice(opt.getExtraPrice())
                                        .priceImpact(opt.getExtraPrice()) // 추후 옵션 할인 대응용 현제 적용없음
                                        .type(opt.getType())
                                        .build()
                                ).collect(Collectors.toList())
                )
                .build();
    }
    public AppliedDiscountDTO toAppliedDiscountDTO(AppliedDiscount entity) {
        return AppliedDiscountDTO.builder()
                .discountId(entity.getDiscount().getId())
                .note(entity.getDiscount().getDescription()) // name 대신 description 사용
                .amount(entity.getAmount())
                .build();
    }
}
