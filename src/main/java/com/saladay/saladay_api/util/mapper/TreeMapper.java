package com.saladay.saladay_api.util.mapper;

import com.saladay.saladay_api.domain.menu.Category;
import com.saladay.saladay_api.domain.menu.Menu;
import com.saladay.saladay_api.domain.menu.MenuInventory;
import com.saladay.saladay_api.domain.menu.MenuOption;
import com.saladay.saladay_api.dto.menuDTO.CategoryDTO;
import com.saladay.saladay_api.dto.menuDTO.MenuDTO;
import com.saladay.saladay_api.dto.menuDTO.MenuInventoryDTO;
import com.saladay.saladay_api.dto.menuDTO.MenuOptionDTO;
import com.saladay.saladay_api.repository.MenuInventoryRepository;
import com.saladay.saladay_api.repository.MenuOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TreeMapper {
    /* 트리 구조로 한번에 매핑하기 위해 작성된 매핑 컴포넌트 */
    private final AvailabilityMapper availabilityMapper;
    private final MenuInventoryRepository menuInventoryRepository;
    private final MenuOptionRepository menuOptionRepository;

    public CategoryDTO toDto(Category category) {
        List<MenuDTO> menuDTOs = category.getMenu().stream()
                .map(this::convertMenu)
                .filter(MenuDTO::isAvailable) // isAvailable이 true 일때만 반환
                .collect(Collectors.toList());

        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .iconPath(category.getIconPath())
                .sortOrder(category.getSortOrder())
                .menus(menuDTOs)
                .build();
    }

    private MenuDTO convertMenu(Menu menu) {
        MenuInventory inventory = menuInventoryRepository.findByMenu(menu);

        boolean isAvailable = availabilityMapper.isMenuAvailable(menu, inventory);

        List<MenuOptionDTO> optionDTOs = menuOptionRepository.findByMenu(menu).stream()
                .map(this::convertOption)
                .collect(Collectors.toList());

        MenuInventoryDTO inventoryDTO = (inventory != null)
                ? convertInventory(inventory)
                : null;

        return MenuDTO.builder()
                .id(menu.getId())
                .name(menu.getName())
                .description(menu.getDescription())
                .price(menu.getPrice())
                .categoryName(menu.getCategory().getName())
                .menuOptions(optionDTOs)
                .menuInventory(inventoryDTO)
                .imageUrl(menu.getImageUrl())
                .isAvailable(isAvailable)
                .build();
    }

    private MenuOptionDTO convertOption(MenuOption option) {
        return MenuOptionDTO.builder()
                .id(option.getId())
                .name(option.getName())
                .extraPrice(option.getExtraPrice())
                .type(option.getType())
                .isDefault(option.isDefault())
                .isRequired(option.isRequired())
                .displayOrder(option.getDisplayOrder())
                .imageUrl(option.getImageUrl())
                .quantity(1) // 기본값, 요청 DTO 에서는 override 가능
                .build();
    }

    private MenuInventoryDTO convertInventory(MenuInventory inventory) {
        int qty;
        if(inventory == null) {
            qty = 0;
        }else{
            qty = inventory.getStockQuantity();
        }
        return MenuInventoryDTO.builder()
                .id(Objects.requireNonNull(inventory).getId())
                .menuId(inventory.getMenu().getId())
                .stockQuantity(qty)
                .soldOutManually(inventory.isSoldOutManually())
                .partialSoldOut(inventory.isPartialSoldOut())
                .thresholdAlert(inventory.getThresholdAlert())
                .build();
    }
}

