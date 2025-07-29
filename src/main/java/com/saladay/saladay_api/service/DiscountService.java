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
// 단건 조회용으로 변경시.
//    public DiscountDTO calculateApplicableDiscount(Menu menu, LocalDateTime now) {
//        List<Discount> candidates = discountRepository.findAllActiveBetween(now);
//
//
//        // STEP 1: 타겟 필터링 (전체 대상, 카테고리, 메뉴 순)
//        List<Discount> applicable = candidates.stream()
//                .filter(d -> isTargetedTo(d, menu))
//                .sorted(Comparator.comparingInt(Discount::getPriority)) // 우선순위 낮은 순
//                .toList();
//
//        // STEP 2: 중첩 불가 처리 → 가장 높은 우선순위 1개만 적용
//        Discount selected = applicable.stream()
//                .filter(v -> !v.isStackable())
//                .findFirst()
//                .orElse(null);
//
//        if (selected == null) {
//            return null;
//        }
//
//        int discountAmount = calculateAmount(menu.getPrice(), selected);
//
//        return DiscountDTO.builder()
//                .discountId(selected.getId())
//                .discountAmount(discountAmount)
//                .description(selected.getDescription())
//                .type(selected.getType())
//                .stackable(selected.isStackable())
//                .priority(selected.getPriority())
//                .targetType(selected.getTargetType())
//                .targetMenuId(selected.getTargetMenuId())
//                .targetCategoryId(selected.getTargetCategoryId())
//                .build();
//    }
//
//    private boolean isTargetedTo(Discount discount, Menu menu) {
//        if (discount.getTargetType() == DiscountTargetType.ALL) return true;
//        if (discount.getTargetType() == DiscountTargetType.CATEGORY) {
//            return Objects.equals(discount.getTargetCategoryId(), menu.getCategory().getId());
//        }
//        if (discount.getTargetType() == DiscountTargetType.MENU) {
//            return Objects.equals(discount.getTargetMenuId(), menu.getId());
//        }
//        return false;
//    }
//
//    private int calculateAmount(int basePrice, Discount discount) {
//        if (discount.getType() == DiscountType.PERCENT) {
//            return basePrice * discount.getValue() / 100;
//        } else {
//            return discount.getValue();
//        }
//    }
//}

    public List<DiscountDTO> calculateApplicableDiscounts(Menu menu, LocalDateTime now) {
        List<Discount> candidates = discountRepository.findAllActiveBetween(now);

        List<Discount> applicable = candidates.stream()
                .filter(d -> isTargetedTo(d, menu))
                .sorted(Comparator.comparingInt(Discount::getPriority))
                .toList();

        // 중첩 불가 중 가장 우선순위 높은 것 하나 선택
        Discount nonStackable = applicable.stream()
                .filter(d -> !d.isStackable())
                .findFirst()
                .orElse(null);

        // 중첩 가능 할인 필터링
        List<Discount> stack = applicable.stream()
                .filter(Discount::isStackable)
                .toList();

        // 최종 적용 할인 리스트 만들기
        List<Discount> finalDiscounts = nonStackable != null
                ? List.of(nonStackable)
                : stack;

        return finalDiscounts.stream()
                .map(d -> {
                    int amount = calculateAmount(menu.getPrice(), d);
                    return toDto(d, amount);
                })
                .collect(Collectors.toList());
    }

    private boolean isTargetedTo(Discount discount, Menu menu) {
        return switch (discount.getTargetType()) {
            case ALL -> true;
            case CATEGORY -> Objects.equals(discount.getTargetCategoryId(), menu.getCategory().getId());
            case MENU -> Objects.equals(discount.getTargetMenuId(), menu.getId());
        };
    }

    private int calculateAmount(int basePrice, Discount discount) {
        return discount.getType() == DiscountType.PERCENT
                ? basePrice * discount.getValue() / 100
                : discount.getValue();
    }

    private DiscountDTO toDto(Discount discount, int amount) {
        return DiscountDTO.builder()
                .discountId(discount.getId())
                .discountAmount(amount)
                .description(discount.getDescription())
                .type(discount.getType())
                .stackable(discount.isStackable())
                .priority(discount.getPriority())
                .targetType(discount.getTargetType())
                .targetMenuId(discount.getTargetMenuId())
                .targetCategoryId(discount.getTargetCategoryId())
                .build();
    }
}
