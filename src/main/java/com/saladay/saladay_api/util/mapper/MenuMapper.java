package com.saladay.saladay_api.util.mapper;

import com.saladay.saladay_api.domain.menu.Category;
import com.saladay.saladay_api.domain.menu.Menu;
import com.saladay.saladay_api.dto.menuDTO.CategoryDTO;
import com.saladay.saladay_api.dto.menuDTO.MenuDTO;
import com.saladay.saladay_api.dto.menuDTO.MenuDetailDTO;
import org.springframework.stereotype.Component;

@Component
public class MenuMapper {

    public CategoryDTO toCategoryDTO(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .sortOrder(category.getSortOrder())
                .build();
    }

    public MenuDTO toMenuDTO(Menu menu) {
        return MenuDTO.builder()
                .id(menu.getId())
                .name(menu.getName())
                .description(menu.getDescription())
                .price(menu.getPrice())
                .categoryName(menu.getCategory().getName())
                .imageUrl(menu.getImageUrl())
                .isAvailable(menu.isActive())
                .build();
    }

//    public MenuDetailDTO toMenuDetailDTO(Menu menu) {
//
//
//
//        return MenuDetailDTO.builder()
//                .id(menu.getId())
//                .name(menu.getName())
//                .description(menu.getDescription())
//                .price(menu.getPrice())
//                .discountedPrice(menu.getDiscountedPrice())
//                .discountDescription(menu.getDiscountDescription())
//                .categoryName(menu.getCategory().getName())
//                .imageUrl(menu.getImageUrl())
//                .isAvailable(menu.isActive())
//                .stockQuantity(menu.getStockQuantity())
//                .optionSummary(menu.getOptionSummary())
//                .visibleFrom(menu.getVisibleFrom())
//                .visibleUntil(menu.getVisibleUntil())
//                .build();
//    }
}
