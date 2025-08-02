package com.saladay.saladay_api.sms;

import jakarta.annotation.PostConstruct;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private DefaultMessageService messageService;
    @Value("${com.saladay.sms.apiKey}")
    private String apiKey;
    @Value("${com.saladay.sms.apiSecretKey}")
    private String apiSecretKey;
    @Value("${com.saladay.sms.domain}")
    private String domain;
    @Value("${com.saladay.sms.from}")
    private String from;

    // 초기화 메서드
    @PostConstruct
    public void init() {
        this.messageService = new DefaultMessageService(apiKey, apiSecretKey, domain);
    }

    // 메시지 전송 메서드
    public SingleMessageSentResponse sendSms(MessageDTO messageDTO) {

        Message message = new Message();
        message.setFrom(from); // 발신자 번호
        message.setTo(messageDTO.getTo());     // 수신자 번호
        message.setText(messageDTO.getText()); // 메시지 내용

        return this.messageService.sendOne(new SingleMessageSendingRequest(message));
    }
}
