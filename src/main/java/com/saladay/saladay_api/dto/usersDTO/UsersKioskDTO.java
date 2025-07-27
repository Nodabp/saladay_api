package com.saladay.saladay_api.dto.usersDTO;

import com.saladay.saladay_api.domain.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersKioskDTO {

    @Schema(description = "사용자 ID", example = "1")
    private Long id;

    @Schema(description = "휴대폰 번호", example = "01012345678")
    private String phoneNumber;

    @Schema(description = "사용자 이름", example = "김샐러")
    private String name;

    @Schema(description = "생년월일", example = "1993-06-15")
    private LocalDateTime birth;

    @Schema(description = "성별", example = "F")
    private Gender gender;

    @Schema(description = "성인 여부", example = "true")
    private boolean isAdult;
}

