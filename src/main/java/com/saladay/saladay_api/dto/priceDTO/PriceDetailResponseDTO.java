package com.saladay.saladay_api.dto.priceDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class PriceDetailResponseDTO {

    private List<PriceDetailDTO> details; // 각 메뉴별 가격 상세
    private int totalBasePrice;           // 총 기본가
    private int totalDiscount;            // 총 할인 금액
    private int totalOptionPrice;         // 총 옵션 추가 금액
    private int totalPointUsed;           // 총 사용 포인트
    private int finalTotalPrice;          // 최종 결제 금액
    private int expectedPointReward;      // 예상 적립 포인트
}

