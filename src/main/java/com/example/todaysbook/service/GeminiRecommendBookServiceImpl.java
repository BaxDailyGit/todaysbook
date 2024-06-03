package com.example.todaysbook.service;

import com.example.todaysbook.domain.dto.BookDto;
import com.example.todaysbook.domain.dto.GeminiRecommendBookDto;
import com.example.todaysbook.domain.entity.Book;
import com.example.todaysbook.domain.entity.GeminiRecommendBook;
import com.example.todaysbook.exception.book.BookNotFoundException;
import com.example.todaysbook.exception.geminiRecommendBook.GeminiRecommendBookNotFoundException;
import com.example.todaysbook.repository.BookRepository;
import com.example.todaysbook.repository.GeminiRecommendBookRepository;
import com.example.todaysbook.util.AladinApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeminiRecommendBookServiceImpl implements GeminiRecommendBookService {

    private final BookRepository bookRepository;
    private final GeminiRecommendBookRepository geminiRecommendBookRepository;
    private final AladinApi aladinApi;

    // 추천된 책 DB에 저장 process
    public void saveGeminiRecommendBook(String message) throws UnsupportedEncodingException {
        // 책 제목 추출
        List<String> bookTitles = extractBookTitles(message);

        log.info("-----------------책 제목 DB 저장 시작-----------------");
        for (String bookTitle : bookTitles) {

            // 책 제목으로 DB 검색
            Optional<Book> bookOptional = bookRepository.findFirstByTitle(bookTitle);
            long bookId;
            if (bookOptional.isPresent()) {
                // DB에 책이 있으면 해당 bookId 가져오기
                bookId = bookOptional.get().getId();
                log.info(bookTitle + ": bookId(" + bookId + ") DB에 있습니다.");
                // 가져온 bookId를 GeminiRecommendBook 엔티티에 저장
                saveGeminiRecommendBookEntity(bookId);

            } else {
                // DB에 책이 없으면 알라딘 API에서 데이터 가져와서 저장
                log.info(bookTitle + ":  title로 검색해본 결과 Book에 없습니다. 알라딘 API에서 검색후 isbn을 가져와 다시 검색합니다.");

                try {
                    HashMap<String, ?> response = aladinApi.getNewBook(bookTitle, 1, 1);
                    List<BookDto> bookList = (List<BookDto>) response.get("books");

                    if (!bookList.isEmpty()) { // API 호출 결과가 있으면
                        BookDto bookDto = bookList.get(0); // 첫 번째 결과 사용
                        Optional<Book> existingBook = bookRepository.findFirstByIsbn(bookDto.getIsbn()); // isbn으로 DB 검색
                        if (existingBook.isPresent()) { // isbn으로 DB 검색 결과 해당 있으면 해당 bookId 가져오기 (즉, 두번 검색)(첫번째는 제목으로, 두번째는 isbn으로)
                            log.info(bookTitle + ":  isbn으로 검색해본 결과 Book에 있습니다.");
                            bookId = existingBook.get().getId();
                            saveGeminiRecommendBookEntity(bookId);
                        } else {
                            log.info(bookTitle + ":  isbn으로 검색해본 결과 Book에 없습니다. 외부 API에서 가져온 데이터를 Book에 저장합니다.");
                            aladinApi.addNewBook(bookDto);
                            Optional<Book> savedBook = bookRepository.findFirstByIsbn(bookDto.getIsbn());
                            savedBook.orElseThrow(() -> new BookNotFoundException("book 저장 실패: " + bookTitle));
                            bookId = savedBook.get().getId();
                            log.info("bookId(" + bookId + ") Book에 저장 완료");
                            saveGeminiRecommendBookEntity(bookId);
                        }
                    } else {
                        log.info(bookTitle + ":  title로 검색해본 결과 알라딘 API에 없습니다. 다음 책으로 넘어갑니다.\n");
                    }
                } catch (Exception e) {
                    log.error("책를 처리하는 중에 오류가 발생했습니다. bookTitle: " + bookTitle, e);
                }
            }
        }
        log.info("-----------------책 제목 DB 저장 완료-----------------\n");
    }

    // 메시지에서 책 제목 추출
    private List<String> extractBookTitles(String message) {
        log.info("---------------책 제목 추출 시작---------------");
        return Arrays.stream(message.split("\n"))
                .map(line -> line.replaceAll("^\\d+\\.\\s*", "").trim())
                .peek(log::info)
                .collect(Collectors.toList());
    }

    // GeminiRecommendBook 저장
    private void saveGeminiRecommendBookEntity(long bookId) {
        GeminiRecommendBook geminiRecommendBook = GeminiRecommendBook.builder()
                .bookId(bookId)
                .date(LocalDateTime.now())
                .build();
        geminiRecommendBookRepository.save(geminiRecommendBook);
        log.info("bookId(" + bookId + ") GeminiRecommendBook에 저장 완료\n");
    }

    // 오늘 추천된 책 목록 반환하기
    public List<GeminiRecommendBookDto> getTodayRecommendBooks() {
        LocalDate today = LocalDate.now();

        List<GeminiRecommendBook> todayRecommendBooks = geminiRecommendBookRepository.findByDateBetween(
                        today.atTime(0, 0, 0), today.atTime(23, 59, 59))
                .stream()
                .distinct()
                .toList();

        return todayRecommendBooks.stream()
                .map(recommend -> {
                    Book book = bookRepository.findById(recommend.getBookId())
                            .orElseThrow(() -> new BookNotFoundException("ook을 찾을수 없습니다. id: " + recommend.getBookId()));
                    BookDto bookDto = new BookDto(book.getId(), book.getTitle(), book.getAuthor(), book.getPrice(), book.getImagePath(), book.getPublisher(), book.getPublishDate(), book.getStock(), book.getIsbn(), book.getDescription(), book.getCategoryId());
                    return new GeminiRecommendBookDto(recommend.getId(), bookDto);
                })
                .collect(Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparingLong(recommend -> recommend.getBookDto().getId()))),
                        ArrayList::new));
    }

    @Transactional
    public boolean deleteBook(Long id) {
        GeminiRecommendBook geminiBook = geminiRecommendBookRepository.findById(id)
                .orElseThrow(() -> new GeminiRecommendBookNotFoundException("GeminiRecommendBook을 찾을수 없습니다. id: " + id));
        Long bookId = geminiBook.getBookId();

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book을 찾을수 없습니다. id: " + bookId));

        // 같은 bookId를 가진 모든 GeminiRecommendBook 삭제
        List<GeminiRecommendBook> booksToDelete = geminiRecommendBookRepository.findByBookId(bookId);
        geminiRecommendBookRepository.deleteAll(booksToDelete);

        return true;
    }
}