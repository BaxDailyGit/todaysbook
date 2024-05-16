package com.example.todaysbook.exception.geminiRecommendBook;

public class GeminiRecommendBookNotFoundException extends RuntimeException {
    public GeminiRecommendBookNotFoundException(String message) {
        super(message);
    }
}