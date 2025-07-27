package com.saladay.saladay_api.dto.menuDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    @Schema(description = "카테고리 고유 ID", example = "1")
    private Long id;

    @Schema(description = "카테고리 이름", example = "샐러드")
    private String name;

    @Schema(description = "카테고리 설명", example = "다양한 채소와 토핑으로 구성된 건강식")
    private String description;

    @Schema(description = "카테고리 순서", example = "1")
    private int sortOrder;
}
