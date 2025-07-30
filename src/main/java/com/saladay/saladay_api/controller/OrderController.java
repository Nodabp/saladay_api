package com.saladay.saladay_api.controller;

import com.saladay.saladay_api.domain.orders.Orders;
import com.saladay.saladay_api.dto.ordersDTO.OrderResponseDTO;
import com.saladay.saladay_api.dto.priceDTO.PriceListRequestDTO;
import com.saladay.saladay_api.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "주문 생성", description = "주문과 옵션 정보를 포함해 새로운 주문을 생성합니다.")
    @PostMapping("/create")
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody PriceListRequestDTO request) {
        OrderResponseDTO order = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

}
