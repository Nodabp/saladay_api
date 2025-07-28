package com.saladay.saladay_api.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
@SpringBootTest
class MenuServiceTest {

    @Autowired
    private MenuService menuService;

    @Test
    void findByIsActiveTrue() {
        menuService.findByIsActiveTrue().forEach(log::info);
    }

    @Test
    void findMenuById() {
       log.info(menuService.findMenuById(2L)) ;
    }
}