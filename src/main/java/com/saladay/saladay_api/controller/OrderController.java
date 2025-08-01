package com.saladay.saladay_api.controller;

import com.saladay.saladay_api.domain.orders.Orders;
import com.saladay.saladay_api.dto.ordersDTO.OrderRequestDTO;
import com.saladay.saladay_api.dto.ordersDTO.OrderResponseDTO;
import com.saladay.saladay_api.dto.priceDTO.PriceListRequestDTO;
import com.saladay.saladay_api.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "주문 생성", description = "주문과 옵션 정보를 포함해 새로운 주문을 생성합니다.")
    @PostMapping
    public OrderResponseDTO createOrder(
            @RequestBody OrderRequestDTO request
    ) {
        String paymentKey = ""; // 추후 승인 검증용.
        return orderService.createOrder(request, paymentKey);
    }
}
