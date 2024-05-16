package com.example.todaysbook.service;

import com.example.todaysbook.constant.Constant;
import com.example.todaysbook.domain.dto.GeminiRecommendApiRequest;
import com.example.todaysbook.domain.dto.GeminiRecommendApiResponse;
import com.example.todaysbook.exception.geminiRecommendBook.GeminiApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GeminiApiServiceImpl implements GeminiApiService {
    @Qualifier("geminiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    @Value("${gemini.api.url}")
    private String apiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @Value("${gemini.generationConfig.candidate_count}")
    private int candidateCount;

    @Value("${gemini.generationConfig.max_output_tokens}")
    private int maxOutputTokens;

    private final GeminiRecommendBookService geminiRecommendBookService;

    // 자동으로 책 추천
    public ResponseEntity<String> AutomaticallycallGeminiApi() {
        String prompt = String.format(Constant.DEFAULT_PROMPT, Constant.DEFAULT_NATION, Constant.DEFAULT_QUANTITY);
        return callGeminiApi(prompt, Constant.DEFAULT_QUANTITY, Constant.DEFAULT_TEMPERATURE);
    }

    // 수동으로 책 추천
    public void ManuallyCallGeminiApi(Integer quantity, Double temperature) {
        quantity = Objects.requireNonNullElse(quantity, Constant.DEFAULT_QUANTITY);
        temperature = Objects.requireNonNullElse(temperature, Constant.DEFAULT_TEMPERATURE);

        String prompt = String.format(Constant.DEFAULT_PROMPT, Constant.DEFAULT_NATION, quantity);
        callGeminiApi(prompt, quantity, temperature);
    }

    // Gemini API 호출
    private ResponseEntity<String> callGeminiApi(String prompt, int quantity, double temperature) {
        try {
            String message = getContents(prompt, temperature);
            geminiRecommendBookService.saveGeminiRecommendBook(message);
            return ResponseEntity.ok(message);
        } catch (HttpClientErrorException e) {
            throw new GeminiApiException("Gemini API 요청 실패", e, e.getStatusCode());
        } catch (RestClientException e) {
            throw new GeminiApiException("Gemini API 요청 실패", e, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (UnsupportedEncodingException e) {
            throw new GeminiApiException("지원되지 않는 인코딩입니다.", e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Gemini API 응답
    private String getContents(String prompt, double temperature) throws UnsupportedEncodingException {
        String requestUrl = apiUrl + "?key=" + geminiApiKey;
        GeminiRecommendApiRequest request = new GeminiRecommendApiRequest(prompt, candidateCount, maxOutputTokens, temperature);
        GeminiRecommendApiResponse response = restTemplate.postForObject(requestUrl, request, GeminiRecommendApiResponse.class);

        if (response == null || response.getCandidates() == null || response.getCandidates().isEmpty()) {
            throw new GeminiApiException("Gemini API 응답이 비었습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response.getCandidates().get(0).getContent().getParts().get(0).getText();
    }
}