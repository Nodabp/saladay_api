package com.saladay.saladay_api.controller;

import com.saladay.saladay_api.dto.pointDTO.PointRequestDTO;
import com.saladay.saladay_api.dto.pointDTO.PointResponseDTO;
import com.saladay.saladay_api.service.PointService;
import com.saladay.saladay_api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/points")
public class PointController {

    private final PointService pointService;
    private final UserService userService;

    @Operation(summary = "handlePoint", description = "포인트 저장 프로세스(POST)")
    @PostMapping
    public ResponseEntity<PointResponseDTO> handlePoint(@RequestBody PointRequestDTO requestDTO) {
        PointResponseDTO dto = pointService.process(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto); // POST 응답은 201 Created
    }

    @Operation(summary = "getPoints", description = "포인트 조회 프로세스(GET)")
    @GetMapping
    public ResponseEntity<List<PointResponseDTO>> getPoints(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String phoneNumber) {

        List<PointResponseDTO> responseList = pointService.getPoints(userId, phoneNumber);

        if (responseList.isEmpty()) { // 결과내역 없을 때 일단 처리
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(responseList);
    }

    @Operation(summary = "getTotalPoint", description = "포인트 합산 프로세스(GET)")
    @GetMapping("/total")
    public ResponseEntity<Integer> getTotalPoint(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String phoneNumber) {

        Long resolvedUserId = (userId != null)
                ? userId
                : userService.findByPhoneNumber(phoneNumber).getId();

        int total = pointService.getTotalPointByUser(resolvedUserId);
        return ResponseEntity.ok(total);
    }
    @Operation(summary = "getAvailablePoint", description = "유효기간 고려한 사용 가능 포인트 조회")
    @GetMapping("/available")
    public ResponseEntity<Integer> getAvailablePoint(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String phoneNumber) {

        Long resolvedUserId = (userId != null)
                ? userId
                : userService.findByPhoneNumber(phoneNumber).getId();

        int available = pointService.getAvailablePointByUser(resolvedUserId);
        return ResponseEntity.ok(available);
    }
    @Operation(summary = "usePoint", description = "포인트 사용 요청")
    @PostMapping("/use")
    public ResponseEntity<String> usePoint(
            @RequestParam Long userId,
            @RequestParam int amountToUse) {

        pointService.usePoint(userId, amountToUse);
        return ResponseEntity.ok("포인트 사용 완료");
    }
}
