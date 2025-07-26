package com.saladay.saladay_api.repository;

import com.saladay.saladay_api.domain.enums.Gender;
import com.saladay.saladay_api.domain.orders.Orders;
import com.saladay.saladay_api.domain.point.Point;
import com.saladay.saladay_api.domain.users.Users;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;


    @Test
    void save() {

        List<Orders> orders = new ArrayList<>();
        List<Point> point = new ArrayList<>();

        Users user = Users.builder()
                .name("test_name1")
                .birth(LocalDate.now())
                .phoneNumber("1234")
                .gender(Gender.M)
                .address1("ad1")
                .address2("ad2")
                .zipcode("zip")
                .orders(orders)
                .points(point)
                .build();

        userRepository.save(user);
    }
    @Test
    void select() {
        userRepository.findAll().forEach(log::info);
    }
    @Test
    void findByPhoneNumber(){
        log.info(userRepository.findByPhoneNumber("1234"));
    }
}