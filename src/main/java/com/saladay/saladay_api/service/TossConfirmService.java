package com.saladay.saladay_api.service;

import com.saladay.saladay_api.domain.enums.OrderStatus;
import com.saladay.saladay_api.domain.enums.PointSource;
import com.saladay.saladay_api.domain.enums.PointType;
import com.saladay.saladay_api.domain.orders.Orders;
import com.saladay.saladay_api.domain.orders.OrdersItem;
import com.saladay.saladay_api.domain.point.Point;
import com.saladay.saladay_api.domain.users.Users;
import com.saladay.saladay_api.dto.pointDTO.PointRequestDTO;
import com.saladay.saladay_api.repository.OrdersItemRepository;
import com.saladay.saladay_api.repository.OrdersRepository;
import com.saladay.saladay_api.repository.PointRepository;
import com.saladay.saladay_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class TossConfirmService {

    private final OrdersRepository ordersRepository;
    private final PointService pointService;
    private final UserRepository userRepository;
    private final PointRepository pointRepository;
    private final MenuInventoryService menuInventoryService;
    private final OrdersItemRepository ordersItemRepository;

    @Transactional
    public Boolean confirmProcess(String paymentKey, String orderId) {
        log.info("confirmProcess_paymentKey: {}, orderId: {}", paymentKey, orderId);
        Orders order = ordersRepository.findByOrderId(orderId);

        if (order == null) {
            log.warn("주문 ID '{}'에 해당하는 주문이 존재하지 않습니다.", orderId);
            return false;
        }

        if (!paymentKey.equals(order.getPaymentKey())) {
            log.warn("결제 키 불일치: 요청된 키 '{}' vs 저장된 키 '{}'", paymentKey, order.getPaymentKey());
            updateOrderStatus(order, OrderStatus.orderFail);
            return false;
        }

        Users users = order.getUsers();
        Optional<Users> user = Optional.empty();
        if (users != null) {
            user = userRepository.findById(users.getId());
        }

        if (order.getStatus() != OrderStatus.payReceive) {
            refundPoints(order, user.orElse(null)); // user가 null일 수 있음
            updateOrderStatus(order, OrderStatus.orderCancel);
            return false;
        }

        earnPoints(order, user.orElse(null)); // user가 null일 수 있음
        updateOrderStatus(order, OrderStatus.shippingComplete);

        List<OrdersItem> ordersItems = ordersItemRepository.findAllByOrdersId(order.getId());
        ordersItems.forEach(item -> {
            menuInventoryService.updateInventoryByMenuId(item.getMenu().getId(), item.getQuantity());
        });
        return true;
    }

    public void refundPoints(Orders order, Users user) {

        int refundAmount = 0;
             List<Point> points = pointRepository.findByRelatedOrderId(order.getId());
             for (Point point : points) {
                 if(point.getType() == PointType.USING) refundAmount += point.getPointAmount();
             }
        log.info("refundPoints 진입");
        if (refundAmount == 0) {
            log.info("환불할 포인트가 없습니다. 주문 ID: {}", order.getId());
            return;
        }

        if (user == null) {
            log.warn("사용자 정보가 없어 환불 포인트 적립을 건너뜁니다. 주문 ID: {}", order.getId());
            return;
        }

        PointRequestDTO refundDTO = PointRequestDTO.builder()
                .userId(user.getId())
                .relatedOrderId(order.getId())
                .pointAmount(refundAmount)
                .type(PointType.EARN)
                .description("결제 실패 환불")
                .source(PointSource.REFERRAL)
                .expiredAt(LocalDateTime.now().plusYears(5))
                .phoneNumber(user.getPhoneNumber())
                .build();

        pointService.process(refundDTO);
        log.info("환불 포인트 {}점 적립 완료 (주문 ID: {})", refundAmount, order.getId());
    }

    private void earnPoints(Orders order, Users user) {
        BigDecimal rewardRate = new BigDecimal("0.05");
        int reward = rewardRate.multiply(BigDecimal.valueOf(order.getTotalPrice())).intValue();

        if (reward <= 0) {
            log.info("적립할 포인트가 없습니다. 주문 ID: {}", order.getId());
            return;
        }
        if (user == null) {
            log.warn("사용자 정보가 없어 포인트 적립을 건너뜁니다. 주문 ID: {}", order.getId());
            return;
        }

        PointRequestDTO earnDTO = PointRequestDTO.builder()
                .userId(user.getId())
                .relatedOrderId(order.getId())
                .pointAmount(reward)
                .type(PointType.EARN)
                .description("결제 포인트 적립")
                .expiredAt(LocalDateTime.now().plusYears(5))
                .phoneNumber(user.getPhoneNumber())
                .build();

        pointService.process(earnDTO);
        log.info("포인트 {}점 적립 완료 (주문 ID: {})", reward, order.getId());
    }


    public void updateOrderStatus(Orders order, OrderStatus newStatus) {
        order.setStatus(newStatus);
        ordersRepository.save(order);
        log.info("주문 상태 변경: {} → {}", order.getOrderId(), newStatus);
    }

    public void updateOrderPaymentKey(Orders orders, String paymentKey) {
        orders.setPaymentKey(paymentKey);
        ordersRepository.save(orders);
        log.info("페이먼트키 변경: {} → {} ", orders.getPaymentKey(), paymentKey);
    }
}