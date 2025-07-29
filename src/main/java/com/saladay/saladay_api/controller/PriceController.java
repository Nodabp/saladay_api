package com.saladay.saladay_api.controller;


import com.saladay.saladay_api.dto.menuDTO.OptionQuantityRequestDTO;
import com.saladay.saladay_api.dto.priceDTO.PriceDetailDTO;
import com.saladay.saladay_api.dto.priceDTO.PriceMenuDetailDTO;
import com.saladay.saladay_api.service.PriceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/kiosk/price")
public class PriceController {

    private final PriceService priceService;

    @Operation(summary = "getMenuDetail", description = "선택 메뉴 값 반환")
    @PostMapping("/{menuId}")
    public ResponseEntity<PriceMenuDetailDTO> getMenuDetail(
            @PathVariable Long menuId,
            @RequestParam Long userId, // 사용자 포인트 기준 적용
            @RequestParam(required = false) int reqPointAmount,
            @RequestBody(required = false)
            List<OptionQuantityRequestDTO> options
    ) {
        PriceMenuDetailDTO dto = priceService.generateMenuDetail(menuId, userId, options, reqPointAmount);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "getPriceDetail", description = "선택 메뉴 값 반환2")
    @PostMapping("/test/{menuId}")
    public ResponseEntity<PriceDetailDTO> getPriceDetail(
            @PathVariable Long menuId,
            @RequestParam Long userId, // 사용자 포인트 기준 적용
            @RequestParam(required = false) int reqPointAmount,
            @RequestBody(required = false)
            List<OptionQuantityRequestDTO> options
    ) {
        PriceDetailDTO dto = priceService.generatePriceDetail(menuId, userId, options, reqPointAmount);
        return ResponseEntity.ok(dto);
    }
}
