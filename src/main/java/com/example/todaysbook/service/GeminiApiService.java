package com.example.todaysbook.service;

import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;

public interface GeminiApiService {
    ResponseEntity<String> AutomaticallycallGeminiApi();
    void ManuallyCallGeminiApi(Integer quantity, Double temperature) throws UnsupportedEncodingException;
}