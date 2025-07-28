package com.saladay.saladay_api.dto.menuDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptionQuantityRequestDTO {
    private Long optionId;
    private int quantity;
}
