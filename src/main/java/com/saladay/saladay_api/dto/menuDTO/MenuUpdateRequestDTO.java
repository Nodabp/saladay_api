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
public class MenuUpdateRequestDTO {
    private String name;
    private String description;
    private Integer price;
    private Boolean isActive;
    private Long categoryId;
    private LocalDateTime visibleFrom;
    private LocalDateTime visibleUntil;
}
