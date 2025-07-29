package com.saladay.saladay_api.dto.priceDTO;

import com.saladay.saladay_api.dto.menuDTO.MenuOptionDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class PriceDetailDTO {

    @Schema(description = "메뉴 ID", example = "1")
    private Long menuId;

    @Schema(description = "메뉴 이름", example = "치킨 시저 샐러드")
    private String name;

    @Schema(description = "기본 가격", example = "8900")
    private int basePrice;

    @Schema(description = "총 할인 금액", example = "1000")
    private int discountAmount;

    @Schema(description = "할인 적용 가격", example = "7900")
    private int discountedPrice;

    @Schema(description = "할인 설명 목록")
    private List<String> discountDescriptions;

    @Schema(description = "적용 포인트", example = "500")
    private int pointAmount;

    @Schema(description = "총 옵션 추가 금액", example = "1200")
    private int totalOptionPrice;

    @Schema(description = "최종 결제 금액", example = "8600")
    private int finalPrice;

    @Schema(description = "선택된 옵션 목록")
    private List<MenuOptionDTO> selectedOptions;

    @Schema(description = "카테고리명", example = "샐러드")
    private String categoryName;

    @Schema(description = "메뉴 이미지")
    private String imageUrl;

    @Schema(description = "판매 가능 여부")
    private boolean isAvailable;

    @Schema(description = "판매 시작일시")
    private LocalDateTime visibleFrom;

    @Schema(description = "판매 종료일시")
    private LocalDateTime visibleUntil;

    @Schema(description = "재고 수량", example = "100")
    private int stockQuantity;

    public List<Integer> getOptionPriceImpacts() {
        return selectedOptions.stream()
                .map(MenuOptionDTO::getPriceImpact)
                .collect(Collectors.toList());
    }

}
