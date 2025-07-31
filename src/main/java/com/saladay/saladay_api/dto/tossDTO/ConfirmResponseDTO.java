package com.saladay.saladay_api.dto.tossDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmResponseDTO {
    private int code;
    private String message;
    private String payStatus;
    private String orderNo;
    private int amount;
}

