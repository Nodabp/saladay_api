package com.saladay.saladay_api.service;

import com.saladay.saladay_api.domain.menu.Menu;
import com.saladay.saladay_api.domain.menu.MenuInventory;
import com.saladay.saladay_api.domain.menu.MenuOption;
import com.saladay.saladay_api.dto.discountDTO.DiscountDTO;
import com.saladay.saladay_api.dto.menuDTO.OptionQuantityRequestDTO;
import com.saladay.saladay_api.dto.priceDTO.PriceDetailDTO;
import com.saladay.saladay_api.dto.menuDTO.MenuOptionDTO;
import com.saladay.saladay_api.repository.MenuInventoryRepository;
import com.saladay.saladay_api.repository.MenuOptionRepository;
import com.saladay.saladay_api.repository.MenuRepository;
import com.saladay.saladay_api.util.mapper.AvailabilityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PriceService {

    private final DiscountService discountService;
    private final PointService pointService;
    private final MenuInventoryRepository inventoryRepository;
    private final MenuOptionRepository menuOptionRepository;
    private final MenuRepository menuRepository;
    private final AvailabilityMapper availabilityMapper;

    public PriceDetailDTO generatePriceDetail(Long menuId, Long userId, List<OptionQuantityRequestDTO> selectedOptionRequests, int reqPointAmount) {
        Menu menu = menuRepository.findById(menuId).orElseThrow();

        // 할인 계산
        List<DiscountDTO> discounts = discountService.calculateApplicableDiscounts(menu, LocalDateTime.now());
        int totalDiscountAmount = discounts.stream().mapToInt(DiscountDTO::getDiscountAmount).sum();
        List<String> descriptions = discounts.stream().map(DiscountDTO::getDescription).toList();

        int discountedPrice = menu.getPrice() - totalDiscountAmount;

        // 포인트 계산
        int pointAvailable = pointService.getAvailablePointByUser(userId);
        int pointAmount = Math.min(reqPointAmount, Math.min(pointAvailable, discountedPrice));

        // 옵션 처리
        List<MenuOption> allOptions = menuOptionRepository.findAllByMenuId(menuId);
        List<MenuOptionDTO> selectedOptions = new ArrayList<>();


        List<OptionQuantityRequestDTO> safeOptionRequests =
                Optional.ofNullable(selectedOptionRequests).orElse(Collections.emptyList());// 옵션 리스트 npe 방지

        selectedOptions = safeOptionRequests.stream()
                    .map(req -> {
                        MenuOption opt = allOptions.stream()
                                .filter(o -> o.getId().equals(req.getOptionId()))
                                .findFirst()
                                .orElseThrow(() -> new IllegalArgumentException("옵션 ID [" + req.getOptionId() + "]를 찾을 수 없습니다."));
                        return MenuOptionDTO.builder()
                                .id(opt.getId())
                                .name(opt.getName())
                                .extraPrice(opt.getExtraPrice())
                                .quantity(req.getQuantity())
                                .isRequired(opt.isRequired())
                                .type(opt.getType())
                                .displayOrder(opt.getDisplayOrder())
                                .isDefault(opt.isDefault())
                                .imageUrl(opt.getImageUrl())
                                .build();
                    }).toList();


        int totalOptionPrice = selectedOptions.stream()
                .mapToInt(opt -> opt.getExtraPrice() * opt.getQuantity())
                .sum();

        // 가용성 판단 (맵퍼로 분리 가능)
        MenuInventory inventory = inventoryRepository.findByMenuId(menuId);
        int stockQuantity = (inventory != null) ? inventory.getStockQuantity() : 0;
        boolean isAvailable = availabilityMapper.isMenuAvailable(menu, inventory);

        return PriceDetailDTO.builder()
                .menuId(menu.getId())
                .name(menu.getName())
                .basePrice(menu.getPrice())
                .discountAmount(totalDiscountAmount)
                .discountedPrice(discountedPrice)
                .discountDescriptions(descriptions)
                .pointAmount(pointAmount)
                .totalOptionPrice(totalOptionPrice)
                .finalPrice(discountedPrice - pointAmount + totalOptionPrice)
                .selectedOptions(selectedOptions)
                .categoryName(menu.getCategory().getName())
                .imageUrl(menu.getImageUrl())
                .isAvailable(isAvailable)
                .visibleFrom(menu.getVisibleFrom())
                .visibleUntil(menu.getVisibleUntil())
                .stockQuantity(stockQuantity)
                .build();
    }
}
