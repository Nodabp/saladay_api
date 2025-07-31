package com.saladay.saladay_api.dto.tossDTO;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmRequestDTO {
    private String payToken;
}
