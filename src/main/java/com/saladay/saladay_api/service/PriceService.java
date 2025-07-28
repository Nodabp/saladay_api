package com.saladay.saladay_api.service;

import com.saladay.saladay_api.domain.menu.Menu;
import com.saladay.saladay_api.domain.menu.MenuOption;
import com.saladay.saladay_api.dto.discountDTO.DiscountDTO;
import com.saladay.saladay_api.dto.menuDTO.PriceMenuDetailDTO;
import com.saladay.saladay_api.dto.menuDTO.MenuOptionDTO;
import com.saladay.saladay_api.repository.MenuInventoryRepository;
import com.saladay.saladay_api.repository.MenuOptionRepository;
import com.saladay.saladay_api.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceService {

    private final DiscountService discountService;
    private final PointService pointService;
    private final MenuInventoryRepository inventoryRepository;
    private final MenuOptionRepository menuOptionRepository;
    private final MenuRepository menuRepository;

    public PriceMenuDetailDTO generateMenuDetail(Long menuId, Long userId, List<Long> selectedOptionIds, int reqPointAmount
    ) {
        Menu menu = menuRepository.findById(menuId).orElseThrow();
        DiscountDTO discount = discountService.calculateApplicableDiscount(menu, LocalDateTime.now());
        int discountedPrice = menu.getPrice() - discount.getDiscountAmount();


        int pointAvailable = pointService.getAvailablePointByUser(userId);
        int pointAmount = Math.min(reqPointAmount, pointAvailable);
        pointAmount = Math.min(pointAmount, discountedPrice); // 과잉 적용 방지

        int stock = inventoryRepository.findByMenuId(menu.getId()).getStockQuantity();

        boolean isAfterVisibleFrom = menu.getVisibleFrom() == null
                || menu.getVisibleFrom().isBefore(LocalDateTime.now());
        boolean isBeforeVisibleUntil = menu.getVisibleUntil() == null
                || LocalDateTime.now().isBefore(menu.getVisibleUntil());

        boolean isAvailable = menu.isActive()
                && isAfterVisibleFrom
                && isBeforeVisibleUntil
                && stock > 0
                && !inventoryRepository.findByMenuId(menu.getId()).isSoldOutManually();

        // 전체 옵션
        List<MenuOption> allOptions = menuOptionRepository.findAllByMenuId(menu.getId());

        // 선택 옵션만 추려내기
        List<MenuOption> selectedOptions = selectedOptionIds == null ? List.of()  : allOptions.stream()
                .filter(opt -> selectedOptionIds.contains(opt.getId()))
                .toList();

        List<MenuOptionDTO> selectedOptionDTOs = selectedOptions.stream()
                .map(opt -> MenuOptionDTO.builder()
                        .id(opt.getId())
                        .name(opt.getName())
                        .extraPrice(opt.getExtraPrice())
                        .isRequired(opt.isRequired())
                        .type(opt.getType())
                        .displayOrder(opt.getDisplayOrder())
                        .isDefault(opt.isDefault())
                        .imageUrl(opt.getImageUrl())
                        .build())
                .toList();

        int totalOptionPrice = selectedOptions.stream()
                .mapToInt(MenuOption::getExtraPrice)
                .sum();

        int finalOptionPrice = discountedPrice - pointAmount + totalOptionPrice;

        return PriceMenuDetailDTO.builder()
                .id(menu.getId())
                .name(menu.getName())
                .description(menu.getDescription())
                .price(menu.getPrice())
                .discountedPrice(discountedPrice)
                .discountDescription(discount.getDescription())
                .pointAmount(pointAmount)
                .finalPrice(finalOptionPrice)
                .options(selectedOptionDTOs)
                .categoryName(menu.getCategory().getName())
                .imageUrl(menu.getImageUrl())
                .isAvailable(isAvailable)
                .visibleFrom(menu.getVisibleFrom())
                .visibleUntil(menu.getVisibleUntil())
                .totalOptionPrice(totalOptionPrice)
                .stockQuantity(stock)
                .build();
    }
}
