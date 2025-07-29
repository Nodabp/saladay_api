package com.saladay.saladay_api.dto.menuDTO;

import com.saladay.saladay_api.domain.enums.OptionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuOptionDTO {

    @Schema(description = "옵션 아이디", example = "1")
    private Long id;

    @Schema(description = "옵션 메뉴명", example = "치즈 추가")
    private String name;

    @Schema(description = "옵션 가격", example = "1000")
    private int extraPrice;

    @Schema(description = "옵션 타입", example = "TOPPING")
    private OptionType type;

    @Schema(description = "옵션 수량", example = "2") // 엔티티에 없는 컬럼.
    private int quantity;

    @Schema(description = "기본 옵션 여부", example = "true")
    private boolean isDefault;

    @Schema(description = "필수 요구 여부", example = "true")
    private boolean isRequired;

    @Schema(description = "옵션 메뉴명", example = "10")
    private int displayOrder;

    @Schema(description = "이미지 위치", example = "/images/option/topping_1.jpg")
    private String imageUrl;
}
