package com.saladay.saladay_api.dto.ordersDTO;

import com.saladay.saladay_api.dto.menuDTO.MenuOptionDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {

    private Long id;
    private Long orderId;                         // 주문 ID
    private Long menuId;                          // 메뉴 ID
    private String menuName;                      // 메뉴 이름
    private int quantity;
    private int finalPrice;                       // 최종 결제 금액
    private List<MenuOptionDTO> selectedOptions;  // 선택 옵션 상세
    private String discountSummary;               // 할인 요약
    private String optionSummary;                 // 옵션 요약
    private LocalDateTime orderedAt;
}
