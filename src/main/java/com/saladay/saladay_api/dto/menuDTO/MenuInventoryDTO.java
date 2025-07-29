package com.saladay.saladay_api.dto.menuDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuInventoryDTO {

    @Schema(description = "인벤토리 아이디", example = "1")
    private Long id;

    @Schema(description = "메뉴 아이디", example = "1")
    private Long menuId;

    @Schema(description = "수량", example = "100")
    private Integer stockQuantity;

    @Schema(description = "관리자에 의해 수동 품절", example = "false")
    private boolean soldOutManually;

    @Schema(description = "구성 요소 일부 품절", example = "false")
    private boolean partialSoldOut;

    @Schema(description = "품절 경고 알림 기준", example = "123")
    private int thresholdAlert;

}
