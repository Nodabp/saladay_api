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
    private final MenuInventoryRepository inventoryRepository;
    private final MenuOptionRepository menuOptionRepository;

    public List<MenuDTO> findByIsActiveTrue() {
        List<Menu> menus = menuRepository.findByIsActiveTrue();
        return menus.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).toList();
    }

    public MenuDTO findMenuById(Long id) {
        return modelMapper.map(menuRepository.findById(id), MenuDTO.class);
    }

    public List<MenuDTO> findByCategoryId(Long categoryId) {
        return menuRepository.findByCategoryId(categoryId).stream()
                .filter(Menu::isActive)
                .map(menu -> modelMapper.map(menu, MenuDTO.class)).toList();
    }
    public CategoryDTO findCategoryById(Long categoryId) {
        return modelMapper.map(categoryRepository.findById(categoryId).orElseThrow(), CategoryDTO.class);
    }
    public List<CategoryDTO> activeCategoryList() {
        return menuRepository.findByIsActiveTrue().stream()
                .map(category -> {
                    CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);
                    List<MenuDTO> menus = menuRepository.findByCategoryId(category.getId()).stream()
                            .filter(Menu::isActive)
                            .map(menu -> modelMapper.map(menu, MenuDTO.class))
                            .toList();
                    categoryDTO.setMenus(menus);
                    return categoryDTO;
                })
                .toList();
    }

    public List<CategoryDTO> getActiveCategoryTree() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map(category -> {
                    CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);

                    List<MenuDTO> menus = menuRepository.findByCategoryId(category.getId()).stream()
                            .filter(Menu::isActive)
                            .map(menu -> {
                                MenuDTO menuDTO = modelMapper.map(menu, MenuDTO.class);

                                // 옵션 포함
                                List<MenuOptionDTO> options = menuOptionRepository.findAllByMenuId(menu.getId()).stream()
                                        .map(opt -> modelMapper.map(opt, MenuOptionDTO.class))
                                        .toList();
                                menuDTO.setMenuOptions(options);

                                // 인벤토리 포함
                                MenuInventory inventory = inventoryRepository.findByMenuId(menu.getId());
                                if (inventory != null) {
                                    menuDTO.setMenuInventory(modelMapper.map(inventory, MenuInventoryDTO.class));
                                }

                                return menuDTO;
                            })
                            .toList();

                    categoryDTO.setMenus(menus);
                    return categoryDTO;
                })
                .toList();
    }



}
