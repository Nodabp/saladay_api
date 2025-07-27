package com.saladay.saladay_api.util.mapper;

import com.saladay.saladay_api.domain.users.Users;
import com.saladay.saladay_api.dto.usersDTO.UsersKioskDTO;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class CustomMapper {

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

    // 확장 예시
    // public boolean isEligibleForCoupon(LocalDateTime birth, LocalDateTime createdAt)
}