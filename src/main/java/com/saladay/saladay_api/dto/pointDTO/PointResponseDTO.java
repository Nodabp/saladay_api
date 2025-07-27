package com.saladay.saladay_api.dto.pointDTO;

import com.saladay.saladay_api.domain.enums.PointSource;
import com.saladay.saladay_api.domain.enums.PointType;
import com.saladay.saladay_api.domain.point.Point;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointResponseDTO {

    @Schema(description = "포인트 ID", example = "1001")
    private Long id;

    @Schema(description = "사용자 ID", example = "1")
    private Long userId;

    @Schema(description = "포인트 금액 (+ 적립, - 사용)", example = "5000")
    private int pointAmount;

    @Schema(description = "포인트 타입 (EARN, USE)")
    private PointType type;

    @Schema(description = "포인트 출처 (SYSTEM, ADMIN, REFERRAL)")
    private PointSource source;

    @Schema(description = "포인트 설명", example = "가입 축하 적립")
    private String description;

    @Schema(description = "포인트 만료 시점", example = "2025-12-31T23:59:59")
    private LocalDateTime expiredAt;

    @Schema(description = "연결된 주문 ID (있을 경우)", example = "20005")
    private Long relatedOrderId;

    @Schema(description = "적립/사용 시점", example = "2025-07-26T22:45:00")
    private LocalDateTime createdAt;

    public static PointResponseDTO from(Point point) {
        return PointResponseDTO.builder()
                .id(point.getId())
                .userId(point.getUsers().getId())
                .pointAmount(point.getPointAmount())
                .type(point.getType())
                .source(point.getSource())
                .description(point.getDescription())
                .expiredAt(point.getExpiredAt())
                .relatedOrderId(point.getRelatedOrderId())
                .createdAt(point.getCreatedAt())
                .build();
    }

}