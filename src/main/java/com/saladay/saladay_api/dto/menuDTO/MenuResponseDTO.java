package com.saladay.saladay_api.dto.menuDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuResponseDTO {
    private Long id;
    private String name;
    private String description;
    private int price;
    private boolean isActive;
    private String categoryName;
    private LocalDateTime visibleFrom;
    private LocalDateTime visibleUntil;
}
