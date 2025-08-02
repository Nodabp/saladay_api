package com.saladay.saladay_api.sms;

import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
@Log4j2
class MessageServiceTest {

    @Autowired(required = false)
    private MessageService messageService;

    @Test
    public void test() {
        MessageDTO messageDTO = MessageDTO.builder()
                .text("test")
                .to("01076452020")
                .build();
        messageService.sendSms(messageDTO);
    }
}