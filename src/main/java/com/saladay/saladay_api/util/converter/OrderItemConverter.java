package com.saladay.saladay_api.util.converter;

import com.saladay.saladay_api.dto.menuDTO.MenuOptionDTO;
import com.saladay.saladay_api.dto.ordersDTO.OrderItemOptionResponseDTO;
import com.saladay.saladay_api.dto.ordersDTO.OrderItemResponseDTO;
import com.saladay.saladay_api.dto.priceDTO.PriceDetailDTO;

import java.util.stream.Collectors;

public class OrderItemConverter {

    public static OrderItemResponseDTO from(PriceDetailDTO priceDetail, int quantity) {

        return OrderItemResponseDTO.builder()
                .menuId(priceDetail.getMenuId())
                .menuName(priceDetail.getName())
                .quantity(quantity)
                .originalPrice(priceDetail.getBasePrice())
                .discountedPrice(priceDetail.getDiscountedPrice())
                .discountSummary(String.join(", ", priceDetail.getDiscountDescriptions()))
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
}
