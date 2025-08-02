package com.saladay.saladay_api.sms;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageDTO {
    private String to;
    private String text;
}
