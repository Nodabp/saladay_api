package com.saladay.saladay_api.dto.usersDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersPhoneLookupRequestDTO {
    /* 휴대폰 번호 요청용 DTO */
    private String phoneNumber;
}
