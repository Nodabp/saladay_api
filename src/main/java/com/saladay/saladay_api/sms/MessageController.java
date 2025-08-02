package com.saladay.saladay_api.sms;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MessageController {

    private final MessageService messageService;

    @Operation(summary = "sms 보내기")
    @PostMapping("/send_sms")
    public ResponseEntity<MessageDTO> sendSms(@RequestBody MessageDTO smsDTO) {
        log.info("sendSms called");
        messageService.sendSms(smsDTO);
        return ResponseEntity.ok(smsDTO);
    }
}
