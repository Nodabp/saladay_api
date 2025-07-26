package com.saladay.saladay_api.dto.pointDTO;

import com.saladay.saladay_api.domain.enums.PointType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointDTO {

    private Long id;
    private String phoneNumber;
    private PointType pointType;
    private int pointAmount;
    private String description;
    private String source;
    private Long relatedOrderId;

}
