package com.saladay.saladay_api.controller;

import com.saladay.saladay_api.dto.usersDTO.UsersKioskDTO;
import com.saladay.saladay_api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UsersController {

    private final UserService userService;

    @Operation(summary = "lookupUser", description = "휴대폰 번호 사용자 정보 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "사용자 없음"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식")
    })
    @GetMapping("/lookup")
    public ResponseEntity<UsersKioskDTO> lookupUser(@RequestParam @Pattern(regexp = "^01[0-9]-?\\d{3,4}-?\\d{4}$") String phoneNumber) {
        /* 휴대폰 번호 기반 사용자 정보 조회 */
        UsersKioskDTO response = userService.findByPhoneNumber(phoneNumber);
        return ResponseEntity.ok(response);
    }
}
