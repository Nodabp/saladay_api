package com.saladay.saladay_api.service;

import com.saladay.saladay_api.domain.discount.Discount;
import com.saladay.saladay_api.domain.enums.DiscountTargetType;
import com.saladay.saladay_api.domain.enums.DiscountType;
import com.saladay.saladay_api.domain.menu.Menu;
import com.saladay.saladay_api.dto.discountDTO.DiscountDTO;
import com.saladay.saladay_api.repository.DiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiscountService {

    private final DiscountRepository discountRepository;

    public DiscountDTO calculateApplicableDiscount(Menu menu, LocalDateTime now) {
        List<Discount> candidates = discountRepository.findAllActiveBetween(now);


        // STEP 1: 타겟 필터링 (전체 대상, 카테고리, 메뉴 순)
        List<Discount> applicable = candidates.stream()
                .filter(d -> isTargetedTo(d, menu))
                .sorted(Comparator.comparingInt(Discount::getPriority)) // 우선순위 낮은 순
                .toList();


        // STEP 2: 중첩 불가 처리 → 가장 높은 우선순위 1개만 적용
        Discount selected = applicable.stream()
                .filter(v -> !v.isStackable())
                .findFirst()
                .orElse(null);

        if (selected == null) {
            return null;
        }

        int discountAmount = calculateAmount(menu.getPrice(), selected);

        return DiscountDTO.builder()
                .discountId(selected.getId())
                .discountAmount(discountAmount)
                .description(selected.getDescription())
                .type(selected.getType())
                .stackable(selected.isStackable())
                .priority(selected.getPriority())
                .targetType(selected.getTargetType())
                .targetMenuId(selected.getTargetMenuId())
                .targetCategoryId(selected.getTargetCategoryId())
                .build();
    }

    private boolean isTargetedTo(Discount discount, Menu menu) {
        if (discount.getTargetType() == DiscountTargetType.ALL) return true;
        if (discount.getTargetType() == DiscountTargetType.CATEGORY) {
            return Objects.equals(discount.getTargetCategoryId(), menu.getCategory().getId());
        }
        if (discount.getTargetType() == DiscountTargetType.MENU) {
            return Objects.equals(discount.getTargetMenuId(), menu.getId());
        }
        return false;
    }

    private int calculateAmount(int basePrice, Discount discount) {
        if (discount.getType() == DiscountType.PERCENT) {
            return basePrice * discount.getValue() / 100;
        } else {
            return discount.getValue();
        }
    }
}
