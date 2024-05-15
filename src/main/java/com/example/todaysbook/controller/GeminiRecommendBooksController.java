package com.example.todaysbook.controller;

import com.example.todaysbook.domain.dto.BookDto;
import com.example.todaysbook.domain.dto.GeminiRecommendBookDto;
import com.example.todaysbook.service.GeminiRecommendBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gemini")
public class GeminiRecommendBooksController {

    private final GeminiRecommendBookService geminiRecommendBookService;

    // 하루에 한번 자동으로 책을 추천
    @Scheduled(cron = "${scheduler.cron.expression}")
    @GetMapping("/ApiCall")
    public ResponseEntity<String> ScheduledGeminiApiCall() {
        return geminiRecommendBookService.callScheduledGeminiApi();
    }

    // 관리자 페이지에서 수동으로 책을 추천
    @PostMapping("/recommendBooks")
    public ResponseEntity<?> recommendBooks(@RequestParam("quantity") int quantity,
                                            @RequestParam("temperature") double temperature) {
        try {
            geminiRecommendBookService.recommendAndSaveBooks(quantity, temperature);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // 관리자 페이지에서 추천된 책 삭제
    @DeleteMapping("/deleteRecommendBook/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id") Long id) {
        boolean deleted = geminiRecommendBookService.deleteBook(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 추천된 책 리스트 index 페이지에 출력
    @GetMapping("/recommendList")
    public List<BookDto> getTodayRecommendBooks() {
        List<GeminiRecommendBookDto> recommendBookDtos = geminiRecommendBookService.getTodayRecommendBooks();
        return recommendBookDtos.stream()
                .map(GeminiRecommendBookDto::getBookDto)
                .collect(Collectors.toList());
    }
}