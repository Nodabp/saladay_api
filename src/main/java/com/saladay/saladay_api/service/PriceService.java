package com.saladay.saladay_api.service;

import com.saladay.saladay_api.domain.menu.Menu;
import com.saladay.saladay_api.domain.menu.MenuOption;
import com.saladay.saladay_api.dto.discountDTO.DiscountDTO;
import com.saladay.saladay_api.dto.menuDTO.OptionQuantityRequestDTO;
import com.saladay.saladay_api.dto.menuDTO.PriceMenuDetailDTO;
import com.saladay.saladay_api.dto.menuDTO.MenuOptionDTO;
import com.saladay.saladay_api.repository.MenuInventoryRepository;
import com.saladay.saladay_api.repository.MenuOptionRepository;
import com.saladay.saladay_api.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceService {

    private final DiscountService discountService;
    private final PointService pointService;
    private final MenuInventoryRepository inventoryRepository;
    private final MenuOptionRepository menuOptionRepository;
    private final MenuRepository menuRepository;

    public PriceMenuDetailDTO generateMenuDetail(Long menuId, Long userId, List<OptionQuantityRequestDTO> selectedOptionRequests
            , int reqPointAmount
    ) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(); // 메뉴 소환
        // 할인 후 금액 정책관련
        List<DiscountDTO> discounts = discountService.calculateApplicableDiscounts(menu, LocalDateTime.now()); // 할인정책 리스트 가져오기

        int totalDiscountAmount = discounts.stream()
                .mapToInt(DiscountDTO::getDiscountAmount) // 최신 기술 int Stream
                .sum();

        int discountedPrice = menu.getPrice() - totalDiscountAmount;

        // 할인 설명 리스트 배열
        List<String> descriptions = new ArrayList<>();
        discounts.forEach(discount -> {
            descriptions.add(discount.getDescription());
        });

        // 포인트 관련

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

        List<MenuOptionDTO> selectedOptionDTOs = allOptions.stream()
                .filter(opt -> selectedOptionRequests.stream()
                        .anyMatch(req -> req.getOptionId().equals(opt.getId())))
                .map(opt -> {
                    int qty = selectedOptionRequests.stream()
                            .filter(req -> req.getOptionId().equals(opt.getId()))
                            .findFirst()
                            .map(OptionQuantityRequestDTO::getQuantity)
                            .orElse(1);

                    return MenuOptionDTO.builder()
                            .id(opt.getId())
                            .name(opt.getName())
                            .extraPrice(opt.getExtraPrice())
                            .quantity(qty)
                            .isRequired(opt.isRequired())
                            .type(opt.getType())
                            .displayOrder(opt.getDisplayOrder())
                            .isDefault(opt.isDefault())
                            .imageUrl(opt.getImageUrl())
                            .build();
                })
                .toList();

        int totalOptionPrice = selectedOptionRequests.stream()
                .mapToInt(req -> {
                    MenuOption opt = allOptions.stream()
                            .filter(o -> o.getId().equals(req.getOptionId()))
                            .findFirst()
                            .orElseThrow();
                    return opt.getExtraPrice() * req.getQuantity();
                })
                .sum();

        // 최종 금액 계산
        int finalOptionPrice = discountedPrice - pointAmount + totalOptionPrice;

        // dto로 매핑후 리턴.
        return PriceMenuDetailDTO.builder()
                .id(menu.getId())
                .name(menu.getName())
                .description(menu.getDescription())
                .price(menu.getPrice())
                .discountedPrice(discountedPrice)
                .discountDescription(descriptions)
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
