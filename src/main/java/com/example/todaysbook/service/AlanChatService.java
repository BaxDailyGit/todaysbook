package com.example.todaysbook.service;

import com.example.todaysbook.domain.dto.AlanChatResponse;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import java.time.Duration;
import java.util.Objects;

@Service
@Slf4j
public class AlanChatService {

    private final WebClient webClient;
    private final String clientId;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    public AlanChatService(RestTemplateBuilder restTemplateBuilder,
                           WebClient.Builder webClientBuilder,
                           @Value("${alan.client-id}") String clientId) {
        this.webClient = webClientBuilder.baseUrl("https://kdt-api-function.azurewebsites.net").build();
        this.restTemplate = restTemplateBuilder.build();
        this.clientId = clientId;
        this.objectMapper = new ObjectMapper()
                .configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    }

    public Flux<ServerSentEvent<AlanChatResponse>> streamAlanResponse(String content) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/question/sse-streaming")
                        .queryParam("content", content)
                        .queryParam("client_id", clientId)
                        .build())
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(String.class)
                .map(responseString -> {
                    try {
                        return objectMapper.readValue(responseString, AlanChatResponse.class);
                    } catch (Exception e) {
                        log.error("Alan response 파싱 에러", e);
                        return new AlanChatResponse();
                    }
                })
                .map(response -> {
                    if (response != null) {
                        return ServerSentEvent.builder(response).build();
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .delayElements(Duration.ofMillis(5));
    }

    // webClient는 delete메소드에 body를 넣기가 어려워서 restTemplate를 사용했습니다.
    public void resetState() {
        String url = "https://kdt-api-function.azurewebsites.net/api/v1/reset-state";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = String.format("{\"client_id\":\"%s\"}", clientId);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Void> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Void.class);

        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() == null) {
            log.info("대화 내용 초기화 성공");
        } else {
            log.error("대화 내용 초기화 오류. 응답 코드: {}", responseEntity.getStatusCode());
        }

        responseEntity.getBody();
    }
}
