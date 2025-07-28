package com.saladay.saladay_api.service;

import com.saladay.saladay_api.domain.menu.Menu;
import com.saladay.saladay_api.dto.menuDTO.MenuDTO;
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
    private final ModelMapper modelMapper;

    public List<MenuDTO> findByIsActiveTrue() {
        List<Menu> menus = menuRepository.findByIsActiveTrue();
        return menus.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).toList();
    }
    public MenuDTO findMenuById(Long id){
        return modelMapper.map(menuRepository.findById(id), MenuDTO.class);
    }
}
