package com.saladay.saladay_api.service;

import com.saladay.saladay_api.domain.menu.Menu;
import com.saladay.saladay_api.domain.menu.MenuInventory;
import com.saladay.saladay_api.domain.menu.MenuOption;
import com.saladay.saladay_api.dto.discountDTO.DiscountDTO;
import com.saladay.saladay_api.dto.menuDTO.OptionQuantityRequestDTO;
import com.saladay.saladay_api.dto.ordersDTO.AppliedDiscountDTO;
import com.saladay.saladay_api.dto.priceDTO.PriceDetailDTO;
import com.saladay.saladay_api.dto.menuDTO.MenuOptionDTO;
import com.saladay.saladay_api.dto.priceDTO.PriceDetailResponseDTO;
import com.saladay.saladay_api.dto.priceDTO.PriceListRequestDTO;
import com.saladay.saladay_api.repository.MenuInventoryRepository;
import com.saladay.saladay_api.repository.MenuOptionRepository;
import com.saladay.saladay_api.repository.MenuRepository;
import com.saladay.saladay_api.util.converter.OrderItemConverter;
import com.saladay.saladay_api.util.mapper.AvailabilityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PriceService {

    private final DiscountService discountService;
    private final PointService pointService;
    private final MenuInventoryRepository inventoryRepository;
    private final MenuOptionRepository menuOptionRepository;
    private final MenuRepository menuRepository;
    private final AvailabilityMapper availabilityMapper;

    public PriceDetailDTO generatePriceDetail(
            Long menuId,
            Long userId,
            List<OptionQuantityRequestDTO> selectedOptionRequests,
            int reqPointAmount,
            int quantity) {

        Menu menu = menuRepository.findById(menuId).orElseThrow();

        List<DiscountDTO> discounts = discountService.calculateApplicableDiscounts(menu, LocalDateTime.now());
        int totalDiscountAmount = discounts.stream().mapToInt(DiscountDTO::getDiscountAmount).sum();
        int discountedPrice = (menu.getPrice() - totalDiscountAmount) * quantity;

        int pointAvailable = pointService.getAvailablePointByUser(userId);
        int pointAmount = Math.min(reqPointAmount, Math.min(pointAvailable, discountedPrice));

        List<MenuOption> allOptions = menuOptionRepository.findAllByMenuId(menuId);
        List<MenuOptionDTO> selectedOptions = Optional.ofNullable(selectedOptionRequests)
                .orElse(Collections.emptyList())
                .stream()
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
                .sum() * quantity;

        MenuInventory inventory = inventoryRepository.findByMenuId(menuId);
        int stockQuantity = (inventory != null) ? inventory.getStockQuantity() : 0;
        boolean isAvailable = availabilityMapper.isMenuAvailable(menu, inventory);
        int finalPrice = Math.max(0, discountedPrice - pointAmount + totalOptionPrice);

        return PriceDetailDTO.builder()
                .menuId(menu.getId())
                .name(menu.getName())
                .basePrice(menu.getPrice() * quantity)
                .discountAmount(totalDiscountAmount * quantity)
                .discountedPrice(discountedPrice)
                .appliedDiscounts(
                        discounts.stream()
                                .map(d -> AppliedDiscountDTO.builder()
                                        .discountId(d.getDiscountId())
                                        .note(d.getDescription())
                                        .amount(d.getDiscountAmount() * quantity) // 개별할인 x 수량
                                        .build())
                                .collect(Collectors.toList())
                )
                .pointAmount(pointAmount)
                .totalOptionPrice(totalOptionPrice)
                .finalPrice(finalPrice)
                .selectedOptions(selectedOptions)
                .categoryName(menu.getCategory().getName())
                .imageUrl(menu.getImageUrl())
                .isAvailable(isAvailable)
                .visibleFrom(menu.getVisibleFrom())
                .visibleUntil(menu.getVisibleUntil())
                .stockQuantity(stockQuantity)
                .build();
    }

    public PriceDetailResponseDTO calculatePriceDetails(PriceListRequestDTO request) {
        List<PriceDetailDTO> details = request.getItems().stream()
                .map(item -> generatePriceDetail(
                        item.getMenuId(),
                        request.getUserId(),
                        item.getOptions(),
                        request.getPointAmount(),
                        item.getQuantity()
                ))
                .toList();

        int totalBase = details.stream().mapToInt(PriceDetailDTO::getBasePrice).sum();
        int totalDiscount = details.stream().mapToInt(PriceDetailDTO::getDiscountAmount).sum();
        int totalOption = details.stream().mapToInt(PriceDetailDTO::getTotalOptionPrice).sum();
        int totalPointUsed = details.stream().mapToInt(PriceDetailDTO::getPointAmount).sum();
        int finalTotal = details.stream().mapToInt(PriceDetailDTO::getFinalPrice).sum();

        return PriceDetailResponseDTO.builder()
                .details(details)
                .totalBasePrice(totalBase)
                .totalDiscount(totalDiscount)
                .totalOptionPrice(totalOption)
                .totalPointUsed(totalPointUsed)
                .finalTotalPrice(finalTotal)
                .expectedPointReward((int)(finalTotal * 0.05)) // 예상값임.
                .build();
    }


}
