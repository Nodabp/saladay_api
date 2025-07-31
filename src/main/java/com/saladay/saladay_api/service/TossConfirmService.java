package com.saladay.saladay_api.service;

import com.saladay.saladay_api.dto.tossDTO.ConfirmRequestDTO;
import com.saladay.saladay_api.dto.tossDTO.ConfirmResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.Map;

@Service
@RequiredArgsConstructor
public class TossConfirmService {

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://pay.toss.im/api/v2")
            .build();

    private final String tossApiKey = "test_gck_docs_Ovk5rk1EwkEbP0W43n07xlzm";

    public ConfirmResponseDTO confirm(ConfirmRequestDTO request) {
        Map<String, Object> payload = Map.of(
                "payToken", request.getPayToken(),
                "apiKey", tossApiKey
        );

        return webClient.post()
                .uri("/confirm")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(ConfirmResponseDTO.class)
                .block();
    }
}

