package com.example.todaysbook.controller;

import com.example.todaysbook.domain.dto.BookDto;
import com.example.todaysbook.domain.dto.GeminiRecommendBookDto;
import com.example.todaysbook.service.GeminiApiService;
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

    private final GeminiApiService geminiApiService;
    private final GeminiRecommendBookService geminiRecommendBookService;

    // 하루에 한번 자동으로 책을 추천 - 매일 밤 0시에 01분에 실행 (초,분,시,일,월,요일,년도)
    @Scheduled(cron = "0 1 0 * * *")
    @GetMapping("/ApiCall")
    public ResponseEntity<String> AutomaticallyGeminiApi() {
        try {
            return geminiApiService.AutomaticallycallGeminiApi();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during automatic API call: " + e.getMessage());
        }
    }

    // 관리자 페이지에서 수동으로 책을 추천
    @PostMapping("/recommendBooks")
    public ResponseEntity<?> ManuallyGeminiApi(@RequestParam("quantity") int quantity,
                                               @RequestParam("temperature") double temperature) {
        try {
            geminiApiService.ManuallyCallGeminiApi(quantity, temperature);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) { // quantity와 temperature가 범위를 벗어나면
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) { // 그 외의 예외
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during manual API call: " + e.getMessage());
        }
    }

    // 관리자 페이지에서 추천된 책 삭제
    @DeleteMapping("/deleteRecommendBook/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id") Long id) {
        try {
            boolean deleted = geminiRecommendBookService.deleteBook(id);
            if (deleted) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the book: " + e.getMessage());
        }
    }

    // 추천된 책 리스트 index 페이지에 출력
    @GetMapping("/recommendList")
    public ResponseEntity<List<BookDto>> getTodayRecommendBooks() {
        try {
            List<GeminiRecommendBookDto> recommendBookDtos = geminiRecommendBookService.getTodayRecommendBooks();
            List<BookDto> bookDtos = recommendBookDtos.stream()
                    .map(GeminiRecommendBookDto::getBookDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(bookDtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}