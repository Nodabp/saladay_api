package com.saladay.saladay_api.dto.ordersDTO;

import com.saladay.saladay_api.dto.menuDTO.OptionQuantityRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {
    /* 주문요청 */
    private Long userId;
    private String orderName;
    private String customerEmail;
    private String customerMobile;
    private String customerName;
    private int totalPrice;
    private List<OrderItemRequestDTO> items;

}