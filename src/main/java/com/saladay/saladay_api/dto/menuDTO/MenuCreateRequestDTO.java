package com.saladay.saladay_api.dto.menuDTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuCreateRequestDTO {
    @NotBlank
    private String name;

    private String description;

    @Min(0)
    private int price;

    private boolean isActive;

    private Long categoryId; // 분류 선택

    private LocalDateTime visibleFrom;
    private LocalDateTime visibleUntil;
}
