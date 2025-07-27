package com.saladay.saladay_api.service;

import com.saladay.saladay_api.domain.users.Users;
import com.saladay.saladay_api.dto.usersDTO.UsersKioskDTO;
import com.saladay.saladay_api.util.mapper.CustomMapper;
import com.saladay.saladay_api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CustomMapper customMapper; // 성인인증 맵퍼 로직 사용용.

    public UsersKioskDTO findByPhoneNumber(String phoneNumber) {
        Users user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> {
                    log.warn("사용자 조회 실패 - 번호 : {}", phoneNumber);
                    return new EntityNotFoundException("사용자를 찾을 수 없습니다.");
                });
        return customMapper.toKioskDto(user);
    }
}