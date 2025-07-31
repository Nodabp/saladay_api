package com.saladay.saladay_api.controller;

import com.saladay.saladay_api.dto.tossDTO.ConfirmRequestDTO;
import com.saladay.saladay_api.dto.tossDTO.ConfirmResponseDTO;
import com.saladay.saladay_api.service.TossConfirmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {

    private final TossConfirmService confirmService;

    @PostMapping("/confirm")
    public ConfirmResponseDTO confirmPayment(@RequestBody ConfirmRequestDTO dto) {
        return confirmService.confirm(dto);
    }
}

