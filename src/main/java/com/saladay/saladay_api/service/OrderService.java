package com.saladay.saladay_api.service;

import com.saladay.saladay_api.domain.enums.OrderStatus;
import com.saladay.saladay_api.domain.orders.Orders;
import com.saladay.saladay_api.domain.orders.OrdersItem;
import com.saladay.saladay_api.domain.orders.OrdersItemOption;
import com.saladay.saladay_api.domain.users.Users;
import com.saladay.saladay_api.dto.menuDTO.MenuOptionDTO;
import com.saladay.saladay_api.dto.ordersDTO.AppliedDiscountDTO;
import com.saladay.saladay_api.dto.ordersDTO.OrderItemRequestDTO;
import com.saladay.saladay_api.dto.ordersDTO.OrderItemResponseDTO;
import com.saladay.saladay_api.dto.ordersDTO.OrderResponseDTO;
import com.saladay.saladay_api.dto.priceDTO.PriceDetailDTO;
import com.saladay.saladay_api.dto.priceDTO.PriceItemRequestDTO;
import com.saladay.saladay_api.dto.priceDTO.PriceListRequestDTO;
import com.saladay.saladay_api.repository.MenuOptionRepository;
import com.saladay.saladay_api.repository.MenuRepository;
import com.saladay.saladay_api.repository.OrdersRepository;
import com.saladay.saladay_api.repository.UserRepository;
import com.saladay.saladay_api.util.converter.OrderItemConverter;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository;
    private final MenuRepository menuRepository;
    private final OrdersRepository ordersRepository;
    private final MenuOptionRepository menuOptionRepository;
    private final PriceService priceService;
    private final OrderItemConverter orderItemConverter;

    @Transactional
    public OrderResponseDTO createOrder(PriceListRequestDTO request) {
        Users user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        List<PriceDetailDTO> details = request.getItems().stream()
                .map(item -> priceService.generatePriceDetail(
                        item.getMenuId(),
                        request.getUserId(),
                        item.getOptions(),
                        request.getPointAmount(),
                        item.getQuantity()))
                .toList();

        Orders order = Orders.builder()
                .users(user)
                .status(OrderStatus.orderReceive)
                .totalPrice(details.stream().mapToInt(PriceDetailDTO::getFinalPrice).sum())
                .orderId(user.getId() + LocalDate.now().toString())
                .build();

        Map<Long, Integer> quantityMap = request.getItems().stream()
                .collect(Collectors.toMap(
                        PriceItemRequestDTO::getMenuId,
                        PriceItemRequestDTO::getQuantity)
                );

        List<OrderItemResponseDTO> itemResponses = new ArrayList<>();

        for (PriceDetailDTO detail : details) {
            int quantity = quantityMap.getOrDefault(detail.getMenuId(), 1);

            OrdersItem item = OrdersItem.builder()
                    .orders(order)
                    .menu(menuRepository.getReferenceById(detail.getMenuId()))
                    .quantity(quantity)
                    .originalPrice(detail.getBasePrice())
                    .discountedPrice(detail.getFinalPrice())
                    .discountSummary(detail.getAppliedDiscounts().stream()
                            .map(AppliedDiscountDTO::getNote)
                            .collect(Collectors.joining(", ")))
                    .optionSummary(detail.getSelectedOptions().stream()
                            .map(MenuOptionDTO::getName)
                            .collect(Collectors.joining(", ")))
                    .build();

            detail.getSelectedOptions().forEach(opt -> {
                OrdersItemOption option = OrdersItemOption.builder()
                        .ordersItem(item)
                        .menuOption(menuOptionRepository.getReferenceById(opt.getId()))
                        .priceImpact(opt.getExtraPrice())
                        .build();
                item.getOptions().add(option);
            });

            order.getItems().add(item);
            itemResponses.add(orderItemConverter.from(detail, quantity));
        }

        Orders savedOrder = ordersRepository.save(order);

        return OrderResponseDTO.builder()
                .id(savedOrder.getId())
                .orderId(savedOrder.getOrderId())
                .menuId(details.get(0).getMenuId())
                .menuName(details.get(0).getName())
                .quantity(quantityMap.values().stream().mapToInt(Integer::intValue).sum())
                .finalPrice(savedOrder.getTotalPrice())
                .selectedOptions(details.stream()
                        .flatMap(d -> d.getSelectedOptions().stream())
                        .toList())
                .discountSummary(details.stream()
                        .flatMap(d -> d.getAppliedDiscounts().stream())
                        .map(AppliedDiscountDTO::getNote)
                        .distinct()
                        .collect(Collectors.joining(", ")))
                .optionSummary(details.stream()
                        .flatMap(d -> d.getSelectedOptions().stream())
                        .map(MenuOptionDTO::getName)
                        .distinct()
                        .collect(Collectors.joining(", ")))
                .orderedAt(savedOrder.getCreatedAt())
                .itemResponses(itemResponses)
                .build();
    }
}