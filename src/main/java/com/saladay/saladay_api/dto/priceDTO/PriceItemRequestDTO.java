package com.saladay.saladay_api.dto.priceDTO;


import com.saladay.saladay_api.dto.menuDTO.OptionQuantityRequestDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceItemRequestDTO {

    @Schema(description = "메뉴 아이디 입력")
    private Long menuId;
    @Schema(description = "메뉴 수량 정보 입력")
    private int quantity;
    @Schema(description = "옵션 정보 입력")
    private List<OptionQuantityRequestDTO> options;
}
