package com.saladay.saladay_api.dto.usersDTO;

import com.saladay.saladay_api.domain.enums.Gender;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersResponseDTO {
    private Long id;
    private String name;
    private String phoneNumber;
    private Gender gender;
    private LocalDateTime birth;
}

