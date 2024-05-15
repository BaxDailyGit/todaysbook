package com.example.todaysbook.service;

import com.example.todaysbook.domain.dto.BookDto;
import com.example.todaysbook.domain.dto.GeminiRecommendApiRequest;
import com.example.todaysbook.domain.dto.GeminiRecommendApiResponse;
import com.example.todaysbook.domain.dto.GeminiRecommendBookDto;
import com.example.todaysbook.domain.entity.Book;
import com.example.todaysbook.domain.entity.GeminiRecommendBook;
import com.example.todaysbook.repository.BookRepository;
import com.example.todaysbook.repository.GeminiRecommendBookRepository;
import com.example.todaysbook.util.AladinApi;
import com.example.todaysbook.constant.Constant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeminiRecommendBookService {

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

    private final BookRepository bookRepository;
    private final GeminiRecommendBookRepository geminiRecommendBookRepository;
    private final AladinApi aladinApi;

    // 자동으로 책 추천
    public ResponseEntity<String> callScheduledGeminiApi() {
        String prompt = String.format(Constant.DEFAULT_PROMPT, Constant.DEFAULT_NATION, Constant.DEFAULT_QUANTITY);
        return callGeminiApi(prompt, Constant.DEFAULT_QUANTITY, Constant.DEFAULT_TEMPERATURE);
    }

    // 수동으로 책 추천
    public void recommendAndSaveBooks(Integer quantity, Double temperature) throws UnsupportedEncodingException {
        quantity = quantity != null ? quantity : Constant.DEFAULT_QUANTITY;
        temperature = temperature != null ? temperature : Constant.DEFAULT_TEMPERATURE;

        String prompt = String.format(Constant.DEFAULT_PROMPT, Constant.DEFAULT_NATION, quantity);
        callGeminiApi(prompt, quantity, temperature);
    }

    // Gemini API 호출
    private ResponseEntity<String> callGeminiApi(String prompt, int quantity, double temperature) {
        try {
            String message = getContents(prompt, temperature);
            saveGeminiRecommendBook(message);
            return ResponseEntity.ok(message);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    // Gemini API 응답
    private String getContents(String prompt, double temperature) throws UnsupportedEncodingException {
        String requestUrl = apiUrl + "?key=" + geminiApiKey;
        GeminiRecommendApiRequest request = new GeminiRecommendApiRequest(prompt, candidateCount, maxOutputTokens, temperature);
        GeminiRecommendApiResponse response = restTemplate.postForObject(requestUrl, request, GeminiRecommendApiResponse.class);
        return response.getCandidates().get(0).getContent().getParts().get(0).getText();
    }

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
                // DB에 책이 없으면 외부 API에서 데이터 가져와서 저장
                log.info(bookTitle + ":  title로 검색해본 결과 Book에 없습니다. 외부 API에서 검색후 isbn을 가져와 다시 검색합니다.");

                HashMap<String, ?> response = aladinApi.getNewBook(bookTitle, 1,1);
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
                        if (savedBook.isPresent()) {
                            bookId = savedBook.get().getId();
                            log.info("bookId(" + bookId + ") Book에 저장 완료");
                            saveGeminiRecommendBookEntity(bookId);
                        }
                    }
                } else {
                    log.info(bookTitle + ":  isbn으로 검색해본 결과 외부 API에서도 없습니다. 다음 책으로 넘어갑니다.\n");
                }
            }
        }
        log.info("-----------------책 제목 DB 저장 완료-----------------\n");
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

    // 메시지에서 책 제목 추출
    private List<String> extractBookTitles(String message) {
        log.info("---------------책 제목 추출 시작---------------");
        return Arrays.stream(message.split("\n"))
                .map(line -> line.replaceAll("^\\d+\\.\\s*", "").trim())
                .peek(log::info)
                .collect(Collectors.toList());
    }

    // 오늘 추천된 책 목록 반한하기
    public List<GeminiRecommendBookDto> getTodayRecommendBooks() {
        LocalDate today = LocalDate.now();

        List<GeminiRecommendBook> todayRecommendBooks = geminiRecommendBookRepository.findByDateBetween(
                        today.atStartOfDay(), today.atTime(23, 59, 59))
                .stream()
                .distinct()
                .toList();

        return todayRecommendBooks.stream()
                .map(recommend -> {
                    Book book = bookRepository.findById(recommend.getBookId())
                            .orElseThrow(() -> new IllegalStateException("Book not found with id: " + recommend.getBookId()));
                    BookDto bookDto = new BookDto(book.getId(), book.getTitle(), book.getAuthor(), book.getPrice(), book.getImagePath(), book.getPublisher(), book.getPublishDate(), book.getStock(), book.getIsbn(), book.getDescription(), book.getCategoryId());
                    return new GeminiRecommendBookDto(recommend.getId(), bookDto);
                })
                .collect(Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparingLong(recommend -> recommend.getBookDto().getId()))),
                        ArrayList::new));
    }

    @Transactional
    public boolean deleteBook(Long id) {
        Optional<GeminiRecommendBook> bookOptional = geminiRecommendBookRepository.findById(id);
        if (bookOptional.isPresent()) {
            GeminiRecommendBook geminiBook = bookOptional.get();
            Long bookId = geminiBook.getBookId();

            Optional<Book> bookOptional2 = bookRepository.findById(bookId);
            if (bookOptional2.isPresent()) {
                Book book = bookOptional2.get();

                // 같은 bookId를 가진 모든 GeminiRecommendBook 삭제
                List<GeminiRecommendBook> booksToDelete = geminiRecommendBookRepository.findByBookId(bookId);
                geminiRecommendBookRepository.deleteAll(booksToDelete);

                return true;
            }
        }
        return false;
    }
}