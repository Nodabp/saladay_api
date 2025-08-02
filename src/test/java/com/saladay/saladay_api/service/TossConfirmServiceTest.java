package com.saladay.saladay_api.service;

import com.saladay.saladay_api.domain.orders.Orders;
import com.saladay.saladay_api.domain.point.Point;
import com.saladay.saladay_api.domain.users.Users;
import com.saladay.saladay_api.repository.PointRepository;
import com.saladay.saladay_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class TossConfirmServiceTest {

    @Autowired
    private TossConfirmService tossConfirmService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PointRepository pointRepository;

    @Test
    void refundPoints() {
//        Orders orders = orderService.getOrder("order_20250803003617306111");
//        log.info("refund test orders = {} ",orders.toString());
//        Optional<Users> users = userRepository.findById(1L);
//        log.info("refund test users = {} ",users);
        pointRepository.findByRelatedOrderId(25L).forEach(point -> {
            log.info("hihi" + point.getPointAmount());
            log.info(point.getType());
        });

//        tossConfirmService.refundPoints(orders,users.orElse(null));
    }
}