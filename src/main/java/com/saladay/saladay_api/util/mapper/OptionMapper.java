package com.saladay.saladay_api.util.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saladay.saladay_api.dto.menuDTO.MenuOptionDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Log4j2
@Component
@RequiredArgsConstructor
public class OptionMapper {

    private final ObjectMapper objectMapper;

    //옵션 DTO 리스트 → JSON 문자열 변환

    public String toJson(List<MenuOptionDTO> options) {
        try {
            return objectMapper.writeValueAsString(options);
        } catch (JsonProcessingException e) {
            log.error("옵션 직렬화 실패", e);
            return "[]";
        }
    }

      //JSON 문자열 → 옵션 DTO 리스트 변환
    public List<MenuOptionDTO> fromJson(String json) {
        try {
            if (json == null || json.isBlank()) return Collections.emptyList();
            return objectMapper.readValue(json, new TypeReference<>() {});
        } catch (Exception e) {
            log.error("옵션 역직렬화 실패", e);
            return Collections.emptyList();
        }
    }
}

