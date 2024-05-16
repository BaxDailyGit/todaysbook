package com.example.todaysbook.service;


import com.example.todaysbook.domain.dto.GeminiRecommendBookDto;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface GeminiRecommendBookService {
    void saveGeminiRecommendBook(String message) throws UnsupportedEncodingException;
    List<GeminiRecommendBookDto> getTodayRecommendBooks();
    boolean deleteBook(Long id);
}