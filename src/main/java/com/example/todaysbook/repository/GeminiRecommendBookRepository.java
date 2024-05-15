package com.example.todaysbook.repository;

import com.example.todaysbook.domain.entity.GeminiRecommendBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GeminiRecommendBookRepository extends JpaRepository<GeminiRecommendBook, Long> {

    List<GeminiRecommendBook> findByDateBetween(LocalDateTime localDateTime, LocalDateTime localDateTime1);
    List<GeminiRecommendBook> findByBookId(Long bookId);
}
