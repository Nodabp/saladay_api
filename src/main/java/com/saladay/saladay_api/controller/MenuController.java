package com.saladay.saladay_api.controller;


import com.saladay.saladay_api.dto.menuDTO.MenuDTO;
import com.saladay.saladay_api.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/kiosk/menus")
public class MenuController {

    private final MenuService menuService;

    @Operation(summary = "단일 키오스크 메뉴 상세 조회")
    @GetMapping("/{menuId}")
    public ResponseEntity<MenuDTO> getMenuDetail(
            @PathVariable Long menuId
            ) {
        MenuDTO dto = menuService.findMenuById(menuId);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "전체 키오스크 메뉴 상세 조회")
    @GetMapping("/active")
    public ResponseEntity<List<MenuDTO>> getMenuDetailAll(
    ) {
        List<MenuDTO> dtos = menuService.findByIsActiveTrue();
        return ResponseEntity.ok(dtos);
    }
}
