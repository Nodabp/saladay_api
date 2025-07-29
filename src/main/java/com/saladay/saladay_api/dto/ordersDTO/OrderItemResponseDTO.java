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
public class OrderItemResponseDTO {

    private Long menuId;
    private String menuName;
    private int quantity;

    // 가격 요약 전체~
    private PriceDetailDTO priceDetail;

    private int originalPrice;
    private int discountedPrice;

    private String discountSummary;
    private String optionSummary;

    private List<OrderItemOptionResponseDTO> options;
}
