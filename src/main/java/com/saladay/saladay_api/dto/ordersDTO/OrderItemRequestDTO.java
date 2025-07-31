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
public class OrderItemRequestDTO {
    private Long menuId;
    private int quantity;
    private List<OptionQuantityRequestDTO> options;
}
