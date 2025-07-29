package com.saladay.saladay_api.dto.menuDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptionQuantityRequestDTO {
    @Schema(description = "옵션 아이디")
    private Long optionId;
    @Schema(description = "옵션 수량")
    private Integer quantity;
}
