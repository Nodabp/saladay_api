package com.saladay.saladay_api.util.mapper;

import com.saladay.saladay_api.domain.users.Users;
import com.saladay.saladay_api.dto.usersDTO.UsersKioskDTO;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class CustomMapper {
    /* 유저를 받아 키오스크 dto로 전환 성인인지 판단 까지 한번에. */
    public UsersKioskDTO toKioskDto(Users user) {
        return UsersKioskDTO.builder()
                .id(user.getId())
                .phoneNumber(user.getPhoneNumber())
                .name(user.getName())
                .birth(user.getBirth())
                .gender(user.getGender())
                .isAdult(isAdult(user.getBirth()))
                .build();
    }

    public boolean isAdult(LocalDateTime birth) {
        return Duration.between(birth, LocalDateTime.now()).toDays() >= (365 * 19);
    }
}