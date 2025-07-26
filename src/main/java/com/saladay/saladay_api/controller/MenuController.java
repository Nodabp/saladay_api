package com.saladay.saladay_api.controller;


import com.saladay.saladay_api.dto.menuDTO.MenuResponseDTO;
import com.saladay.saladay_api.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menus")
public class MenuController {

    private final MenuService menuService;

    @GetMapping
    public ResponseEntity<MenuResponseDTO> getAllMenu() {

        return null;
    }
}
