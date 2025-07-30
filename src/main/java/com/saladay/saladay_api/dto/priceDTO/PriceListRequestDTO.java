package com.saladay.saladay_api.dto.priceDTO;


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
public class PriceListRequestDTO {

    @Schema(description = "아이디 입력")
    private Long userId;
    @Schema(description = "포인트 입력")
    private int pointAmount;
    @Schema(description = "메뉴 정보 입력")
    private List<PriceItemRequestDTO> items;

}
