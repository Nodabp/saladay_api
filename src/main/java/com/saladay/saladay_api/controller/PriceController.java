package com.saladay.saladay_api.controller;


import com.saladay.saladay_api.dto.menuDTO.OptionQuantityRequestDTO;
import com.saladay.saladay_api.dto.priceDTO.PriceDetailDTO;
import com.saladay.saladay_api.dto.priceDTO.PriceDetailResponseDTO;
import com.saladay.saladay_api.dto.priceDTO.PriceListRequestDTO;
import com.saladay.saladay_api.service.PriceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/price")
public class PriceController {

    private final PriceService priceService;

    @Operation(summary = "calculatePriceDetails", description = "수량 요청")
    @PostMapping("/details")
    public PriceDetailResponseDTO calculatePriceDetails(@RequestBody PriceListRequestDTO request) {
        return priceService.calculatePriceDetails(request);
    }
}
