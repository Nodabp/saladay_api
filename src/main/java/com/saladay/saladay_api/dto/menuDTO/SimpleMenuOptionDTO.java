package com.saladay.saladay_api.dto.menuDTO;


import com.saladay.saladay_api.domain.enums.OptionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleMenuOptionDTO {
    /* Json에 저장할 데이터 */

    private String name;
    private OptionType type;
    private int quantity;
}
