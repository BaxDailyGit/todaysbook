package com.example.todaysbook.service;

import com.example.todaysbook.domain.dto.AlanChatResponse;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;

public interface AlanChatService {
    Flux<ServerSentEvent<AlanChatResponse>> streamAlanResponse(String content);
    void resetState();
}