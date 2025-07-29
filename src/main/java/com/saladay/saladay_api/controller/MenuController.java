package com.saladay.saladay_api.controller;


import com.saladay.saladay_api.dto.menuDTO.CategoryDTO;
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

    @Operation(summary = "카테고리별 메뉴 전체 반환")
    @GetMapping("/category/active")
    public ResponseEntity<List<CategoryDTO>> getCategoryList(
    ) {
        List<CategoryDTO> dtos = menuService.getActiveCategoryTree();
        return ResponseEntity.ok(dtos);
    }
}
