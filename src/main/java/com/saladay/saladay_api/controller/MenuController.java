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
//
//    @Operation(summary = "단일 키오스크 메뉴 상세 조회")
//    @GetMapping("/{menuId}")
//    public ResponseEntity<MenuDTO> getMenuDetail(
//            @PathVariable Long menuId
//            ) {
//        MenuDTO dto = menuService.findMenuById(menuId);
//        return ResponseEntity.ok(dto);
//    }
//
//    @Operation(summary = "전체 키오스크 메뉴 상세 조회")
//    @GetMapping("/activeMenu")
//    public ResponseEntity<List<MenuDTO>> getMenuDetailAll(
//    ) {
//        List<MenuDTO> dtos = menuService.findByIsActiveTrue();
//        return ResponseEntity.ok(dtos);
//    }
//
//    @Operation(summary = "카테고리 아이디에 따른 카테고리별 메뉴 반환")
//    @GetMapping("/category/{categoryId}")
//    public ResponseEntity<List<MenuDTO>> getMenuByCategory(
//            @PathVariable Long categoryId
//    ) {
//        List<MenuDTO> dtos = menuService.findByCategoryId(categoryId);
//        return ResponseEntity.ok(dtos);
//    }
    @Operation(summary = "카테고리별 메뉴 전체 반환")
    @GetMapping("/category/active")
    public ResponseEntity<List<CategoryDTO>> getCategoryList(
    ) {
        List<CategoryDTO> dtos = menuService.getActiveCategoryTree();
        return ResponseEntity.ok(dtos);
    }
}
