package com.example.todaysbook.controller;

import com.example.todaysbook.domain.dto.AlanChatResponse;
import com.example.todaysbook.service.AlanChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@RequestMapping("/alan")
@RestController
public class AlanChatController {

    private final AlanChatService alanChatService;

    @GetMapping("/sse-streaming")
    public Flux<ServerSentEvent<AlanChatResponse>> streamAlanResponse(@RequestParam("content") String content) {
        try {
            if (content == null || content.isEmpty()) {
                throw new IllegalArgumentException("content가 null이거나 비어 있습니다.");
            }
            return alanChatService.streamAlanResponse(content);
        } catch (IllegalArgumentException e) {
            return Flux.error(e);
        } catch (Exception e) {
            return Flux.error(new RuntimeException("요청을 처리하는 동안 오류가 발생했습니다.", e));
        }
    }

    @GetMapping("/reset-state")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void resetState() {
        try {
            alanChatService.resetState();
        } catch (Exception e) {
            throw new RuntimeException("상태를 재설정하는 중에 오류가 발생했습니다.", e);
        }
    }
}