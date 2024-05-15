package com.example.todaysbook.domain.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class GeminiRecommendBookDto {
    private long id;
    private BookDto bookDto;
}