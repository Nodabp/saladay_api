package com.saladay.saladay_api.service;

import com.saladay.saladay_api.domain.enums.OrderStatus;
import com.saladay.saladay_api.domain.enums.PointType;
import com.saladay.saladay_api.domain.menu.Menu;
import com.saladay.saladay_api.domain.orders.AppliedDiscount;
import com.saladay.saladay_api.domain.orders.Orders;
import com.saladay.saladay_api.domain.orders.OrdersItem;
import com.saladay.saladay_api.domain.users.Users;
import com.saladay.saladay_api.dto.ordersDTO.AppliedDiscountDTO;
import com.saladay.saladay_api.dto.ordersDTO.OrderRequestDTO;
import com.saladay.saladay_api.dto.ordersDTO.OrderResponseDTO;
import com.saladay.saladay_api.dto.pointDTO.PointRequestDTO;
import com.saladay.saladay_api.dto.priceDTO.PriceDetailDTO;
import com.saladay.saladay_api.repository.*;
import com.saladay.saladay_api.util.mapper.OptionMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrdersRepository ordersRepository;
    private final UserRepository usersRepository;
    private final MenuRepository menuRepository;
    private final OrdersItemRepository ordersItemRepository;
    private final AppliedDiscountRepository appliedDiscountRepository;
    private final PriceService priceService;
    private final PointService pointService;
    private final DiscountRepository discountRepository;
    private final OptionMapper optionMapper;

    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO request, String paymentKey) {

        Users user = (request.getUserId() != null)
                ? usersRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("회원 없음"))
                : null;

        // 주문 생성
        Orders order = Orders.builder()
                .users(user)
                .orderId("order_"+ LocalDateTime.now().toString().replaceAll("[^0-9]", "").trim())
                .customerName(request.getCustomerName())
                .customerMobile(request.getCustomerMobile())
                .customerEmail(request.getCustomerEmail())
                .paymentKey(paymentKey)
                .status(OrderStatus.orderReceive)
                .build();
        ordersRepository.save(order);

        // 각 주문 아이템 생성
        List<PriceDetailDTO> orderDetails = request.getItems().stream().map(itemReq -> {
            PriceDetailDTO detail = priceService.generatePriceDetail(
                    itemReq.getMenuId(),
                    request.getUserId(),
                    itemReq.getOptions(),
                    request.getPointAmount(),
                    itemReq.getQuantity()
            );


            Menu menu = menuRepository.findById(itemReq.getMenuId())
                    .orElseThrow(() -> new RuntimeException("메뉴 없음"));

            // 옵션 JSON 변환
            String optionSummary = optionMapper.toJson(detail.getSelectedOptions());

            OrdersItem orderItem = OrdersItem.builder()
                    .orders(order)
                    .menu(menu)
                    .quantity(itemReq.getQuantity())
                    .originalPrice(detail.getBasePrice())
                    .discountedPrice(detail.getFinalPrice() / itemReq.getQuantity())
                    .discountSummary(detail.getAppliedDiscounts().stream()
                            .map(AppliedDiscountDTO::getNote)
                            .collect(Collectors.joining(", ")))
                    .optionSummary(optionSummary)
                    .build();

            ordersItemRepository.save(orderItem);

            // 할인 저장
            detail.getAppliedDiscounts().forEach(d -> {
                AppliedDiscount applied = AppliedDiscount.builder()
                        .ordersItem(orderItem)
                        .amount(d.getAmount())
                        .appliedNote(d.getNote())
                        .discount(discountRepository.getReferenceById(d.getDiscountId()))
                        .build();
                appliedDiscountRepository.save(applied);
            });

            return detail;
        }).collect(Collectors.toList());

        int finalTotal = orderDetails.stream()
                .mapToInt(PriceDetailDTO::getFinalPrice)
                .sum();

        order.setTotalPrice(finalTotal);

        order.setStatus(OrderStatus.shippingProgress);
        order.setPaidAt(LocalDateTime.now());


        // 포인트 적립
        if (user != null) pointService.userPointForOrder(user.getId(), request.getPointAmount(), order.getId());

        // 로그확인
        log.info("createOrder_finalTotal: {}", finalTotal);

        OrderResponseDTO dto = OrderResponseDTO.builder()
                .orderId(order.getOrderId())
                .orderStatus(order.getStatus().name())
                .totalPrice(finalTotal)
                .paymentKey(paymentKey)
                .orderDetails(orderDetails)
                .build();
        log.info(dto.getTotalPrice());

        return dto;
    }

    public Orders getOrder(String tossOrderId) {
        return ordersRepository.findByOrderId(tossOrderId);
    }


    public String orderCompletePhoneNumber(String orderId) {
        Orders order = getOrder(orderId); // 한 번만 호출
        if (order != null) {
            String mobile = order.getCustomerMobile();
            if (mobile != null && !mobile.isEmpty()) {
                return mobile;
            }
        }
        return "no-phone";
    }
}
