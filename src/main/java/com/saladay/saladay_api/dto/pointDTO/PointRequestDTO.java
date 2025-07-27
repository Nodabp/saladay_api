package com.saladay.saladay_api.dto.pointDTO;

import com.saladay.saladay_api.domain.enums.PointSource;
import com.saladay.saladay_api.domain.enums.PointType;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointRequestDTO {

    @Schema(description = "사용자 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long userId;

    @Schema(description = "포인트 금액 (+ 적립, - 사용)", example = "5000", requiredMode = Schema.RequiredMode.REQUIRED)
    private int pointAmount;

    @Schema(description = "포인트 타입 (EARN or USE)", requiredMode = Schema.RequiredMode.REQUIRED)
    private PointType type;

    @Schema(example = "01012345678")
    private String phoneNumber;

    @Schema(description = "포인트 출처 (SYSTEM, ADMIN, REFERRAL)", requiredMode = Schema.RequiredMode.REQUIRED)
    private PointSource source;

    @Schema(description = "포인트 설명", example = "리뷰 작성 보상")
    private String description;

    @Schema(description = "포인트 만료 시점 (nullable)", example = "2025-12-31T23:59:59")
    private LocalDateTime expiredAt;

    @Schema(description = "관련 주문 ID (nullable)", example = "10001")
    private Long relatedOrderId;
}

