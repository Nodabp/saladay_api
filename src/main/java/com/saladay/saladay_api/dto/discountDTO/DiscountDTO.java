package com.saladay.saladay_api.dto.discountDTO;

import com.saladay.saladay_api.domain.enums.DiscountTargetType;
import com.saladay.saladay_api.domain.enums.DiscountType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountDTO {

    @Schema(description = "적용된 할인 정책 ID", example = "42")
    private Long discountId;

    @Schema(description = "적용된 할인액", example = "1200")
    private int discountAmount;

    @Schema(description = "할인 정책 설명", example = "여름 한정 이벤트 할인")
    private String description;

    @Schema(description = "적용된 할인 타입", example = "PERCENT")
    private DiscountType type;

    @Schema(description = "정책 중첩 가능 여부", example = "false")
    private boolean stackable;

    @Schema(description = "정책 우선순위", example = "1")
    private int priority;

    @Schema(description = "타겟 유형", example = "MENU")
    private DiscountTargetType targetType;

    @Schema(description = "할인 대상 메뉴 ID", example = "101")
    private Long targetMenuId;

    @Schema(description = "할인 대상 카테고리 ID", example = "5")
    private Long targetCategoryId;
}
