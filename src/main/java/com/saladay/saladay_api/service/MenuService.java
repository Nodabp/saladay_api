package com.saladay.saladay_api.service;

import com.saladay.saladay_api.domain.menu.Menu;
import com.saladay.saladay_api.dto.menuDTO.MenuResponseDTO;
import com.saladay.saladay_api.repository.CategoryRepository;
import com.saladay.saladay_api.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Log4j2
@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public List<MenuResponseDTO> getAllMenu() {
        List<Menu> menus = menuRepository.findAll();
        return menus.stream().map(menu -> modelMapper.map(menu, MenuResponseDTO.class)).toList();
    }
    public List<MenuResponseDTO> getMenuByCategoryId(Long categoryId) {
        List<Menu> menus = menuRepository.findByCategoryId(categoryId);
        return menus.stream().map(menu -> modelMapper.map(menu, MenuResponseDTO.class)).toList();
    }
    public List<MenuResponseDTO> getActiveMenu() {
        List<Menu> menus = menuRepository.findByIsActiveTrue();
        return menus.stream().map(menu -> modelMapper.map(menu, MenuResponseDTO.class)).toList();
    }
}
