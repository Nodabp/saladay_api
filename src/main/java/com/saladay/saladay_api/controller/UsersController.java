package com.saladay.saladay_api.controller;

import com.saladay.saladay_api.dto.usersDTO.UsersResponseDTO;
import com.saladay.saladay_api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UsersController {

    private final UserService userService;

    @Operation(summary = "lookupUser", description = "휴대폰 번호 사용자 정보 조회")
    @GetMapping("/lookup")
    public ResponseEntity<UsersResponseDTO> lookupUser(@RequestParam String phoneNumber) {
        /* 휴대폰 번호 기반 사용자 정보 조회 */
        UsersResponseDTO response = userService.findByPhoneNumber(phoneNumber);
        return ResponseEntity.ok(response);
    }
}
