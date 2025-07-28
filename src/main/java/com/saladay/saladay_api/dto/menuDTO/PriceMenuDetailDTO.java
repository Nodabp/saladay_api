package com.saladay.saladay_api.dto.menuDTO;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceMenuDetailDTO {

    @Schema(description = "메뉴 고유 ID", example = "101")
    private Long id;

    @Schema(description = "메뉴 이름", example = "그릴드 치킨 샐러드")
    private String name;

    @Schema(description = "메뉴 상세 설명", example = "그릴 치킨과 그린믹스, 시저 드레싱이 어우러진 고단백 샐러드입니다.")
    private String description;

    @Schema(description = "정가 (원)", example = "8900")
    private int price;

    @Schema(description = "할인 적용 후 가격", example = "8000")
    private int discountedPrice;

    @Schema(description = "할인 설명", example = "여름 한정 이벤트 할인")
    private String discountDescription;

    @Schema(description = "카테고리 이름", example = "샐러드")
    private String categoryName;

    @Schema(description = "메뉴 대표 이미지 URL", example = "/images/grilled_chicken_salad.jpg")
    private String imageUrl;

    @Schema(description = "메뉴 판매 가능 여부", example = "true")
    private boolean isAvailable;

    @Schema(description = "남은 재고 수량", example = "28")
    private int stockQuantity;

    @Schema(description = "포인트 사용 금액(3000원 이상)" , example = "3000")
    private int pointAmount;

    @Schema(description = "메뉴 옵션", example = "[\"TOPPING\",]")
    private List<MenuOptionDTO> options;

    @Schema(description = "메뉴 옵션 포함 총 가격", example = "10000000")
    private int totalOptionPrice;

    @Schema(description = "최종 가격", example = "30000")
    private int finalPrice;

    @Schema(description = "판매 시작 시간", example = "2025-07-01T00:00:00")
    private LocalDateTime visibleFrom;

    @Schema(description = "판매 종료 시간", example = "2025-08-31T23:59:59")
    private LocalDateTime visibleUntil;
}
