package com.example.todaysbook.exception.geminiRecommendBook;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class GeminiApiException extends RuntimeException {
    private final HttpStatusCode httpStatusCode;

    public GeminiApiException(String message, HttpStatusCode httpStatusCode) {
        super(message);
        this.httpStatusCode = httpStatusCode;
    }

    public GeminiApiException(String message, Throwable cause, HttpStatusCode httpStatusCode) {
        super(message, cause);
        this.httpStatusCode = httpStatusCode;
    }

}