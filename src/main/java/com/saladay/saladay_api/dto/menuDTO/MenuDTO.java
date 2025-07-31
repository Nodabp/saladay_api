package com.saladay.saladay_api.dto.menuDTO;


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
public class MenuDTO {

    @Schema(description = "메뉴 고유 ID", example = "1")
    private Long id;

    @Schema(description = "메뉴 이름", example = "치킨 시저 샐러드")
    private String name;

    @Schema(description = "메뉴 설명", example = "신선한 야채와 구운 치킨, 시저 드레싱이 조화로운 메뉴입니다.")
    private String description;

    @Schema(description = "메뉴 가격 (단위: 원)", example = "8900")
    private int price;

    @Schema(description = "카테고리명", example = "샐러드")
    private String categoryName;

    @Schema(description = "옵션 목록")
    private List<MenuOptionDTO> menuOptions;

    // 메뉴자체의 총 수량 정보,
    @Schema(description = "수량 정보")
    private MenuInventoryDTO menuInventory;

    @Schema(description = "대표 이미지 URL", example = "/images/chicken_caesar.jpg")
    private String imageUrl;

    // Menu 엔티티에 있는 visible_from 과 visible_until  최종 판매 여부를 사용할 컬럼.
    @Schema(description = "판매 가능 여부", example = "true")
    private boolean isAvailable;

    @Schema(description = "강제품절 여부", example = "true")
    private boolean isActive;
}

