package com.saladay.saladay_api.service;

import com.saladay.saladay_api.domain.users.Users;
import com.saladay.saladay_api.dto.usersDTO.UsersPhoneLookupRequestDTO;
import com.saladay.saladay_api.dto.usersDTO.UsersResponseDTO;
import com.saladay.saladay_api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UsersResponseDTO findByPhoneNumber(String phoneNumber) {
        Users user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));
        return modelMapper.map(user, UsersResponseDTO.class);
    }
}