package com.saladay.saladay_api.service;

import com.saladay.saladay_api.domain.menu.Category;
import com.saladay.saladay_api.domain.menu.Menu;
import com.saladay.saladay_api.domain.menu.MenuInventory;
import com.saladay.saladay_api.domain.menu.MenuOption;
import com.saladay.saladay_api.dto.menuDTO.CategoryDTO;
import com.saladay.saladay_api.dto.menuDTO.MenuDTO;
import com.saladay.saladay_api.dto.menuDTO.MenuInventoryDTO;
import com.saladay.saladay_api.dto.menuDTO.MenuOptionDTO;
import com.saladay.saladay_api.repository.CategoryRepository;
import com.saladay.saladay_api.repository.MenuInventoryRepository;
import com.saladay.saladay_api.repository.MenuOptionRepository;
import com.saladay.saladay_api.repository.MenuRepository;
import com.saladay.saladay_api.util.mapper.TreeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class MenuService {

    private final CategoryRepository categoryRepository;
    private final TreeMapper treeMapper;

    public List<CategoryDTO> getActiveCategoryTree() {
        List<Category> categories = categoryRepository.findAll(); // + EntityGraph

        return categories.stream()
                .map(treeMapper::toDto) // TreeMapper 에서 menus, options, inventory 매핑
                .toList();
    }
}
