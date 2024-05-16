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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import java.time.Duration;

@Service
@Slf4j
public class AlanChatServiceImpl implements AlanChatService {

    private final WebClient webClient;
    private final String clientId;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    public AlanChatServiceImpl(RestTemplateBuilder restTemplateBuilder,
                           WebClient.Builder webClientBuilder,
                           @Value("${alan.client-id}") String clientId) {
        this.webClient = webClientBuilder.baseUrl("https://kdt-api-function.azurewebsites.net").build();
        this.restTemplate = restTemplateBuilder.build();
        this.clientId = clientId;
        this.objectMapper = new ObjectMapper()
                .configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    }

    public Flux<ServerSentEvent<AlanChatResponse>> streamAlanResponse(String content) {
        try {
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
                            throw new RuntimeException("Alan response 파싱 중 오류가 발생했습니다.", e);
                        }
                    })
                    .map(response -> ServerSentEvent.builder(response).build())
                    .onErrorResume(e -> {
                        log.error("Alan 응답 스트리밍 중 오류 발생", e);
                        return Flux.error(e);
                    })
                    .delayElements(Duration.ofMillis(5));
        } catch (Exception e) {
            log.error("Alan 응답 스트리밍 요청 중 오류 발생", e);
            return Flux.error(e);
        }
    }

    public void resetState() {
        String url = "https://kdt-api-function.azurewebsites.net/api/v1/reset-state";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = String.format("{\"client_id\":\"%s\"}", clientId);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Void> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Void.class);

            if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() == null) {
                log.info("대화 내용 초기화 성공");
            } else {
                log.error("대화 내용 초기화 오류. 응답 코드: {}", responseEntity.getStatusCode());
                throw new RuntimeException("대화 내용 초기화 중 오류가 발생했습니다.");
            }
        } catch (RestClientException e) {
            log.error("대화 내용 초기화 요청 중 오류 발생", e);
            throw new RuntimeException("대화 내용 초기화 요청 중 오류가 발생했습니다.", e);
        }
    }
}