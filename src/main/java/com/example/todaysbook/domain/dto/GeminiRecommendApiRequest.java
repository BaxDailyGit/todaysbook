package com.example.todaysbook.domain.dto;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeminiRecommendApiRequest {
    private List<Content> contents;
    private GenerationConfig generationConfig;

    @Getter
    @Setter
    public static class Content {
        private Parts parts;
    }

    @Getter @Setter
    public static class Parts {
        private String text;
    }

    @Getter @Setter
    public static class GenerationConfig {
        private int candidate_count;
        private int max_output_tokens;
        private double temperature;
    }

    public GeminiRecommendApiRequest(String prompt,
                       @Value("${gemini.generationConfig.candidate_count}") int candidateCount,
                       @Value("${gemini.generationConfig.max_output_tokens}") int maxOutputTokens,
                       @Value("${gemini.generationConfig.temperature}") double temperature) {
        this.contents = new ArrayList<>();
        Content content = new Content();
        Parts parts = new Parts();

        parts.setText(prompt);
        content.setParts(parts);

        this.contents.add(content);
        this.generationConfig = new GenerationConfig();
        this.generationConfig.setCandidate_count(candidateCount);
        this.generationConfig.setMax_output_tokens(maxOutputTokens);
        this.generationConfig.setTemperature(temperature);
    }
}