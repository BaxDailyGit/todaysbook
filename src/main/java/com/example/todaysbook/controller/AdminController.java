package com.example.todaysbook.controller;

import com.example.todaysbook.domain.dto.*;
import com.example.todaysbook.domain.entity.User;
import com.example.todaysbook.service.*;
import com.example.todaysbook.util.Pagination;
import com.example.todaysbook.util.UserChecker;
import com.example.todaysbook.validate.AdminUpdateBookValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    private final RecommendBookService recommendBookService;
    private final ReviewService reviewService;
    private final SalesService salesService;
    private final OrderService orderService;
    private final UserService userService;
    private final GeminiRecommendBookService geminiRecommendBookService;

    //유저 관리
    @GetMapping("/userlist")
    public String allUserList(@PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                              Model model) {
        Page<AdminUserDto> allUserList = adminService.findAllUser(pageable);

        HashMap<String, Integer> pages = Pagination.calculatePage(allUserList.getPageable().getPageNumber(), allUserList.getTotalPages());
        int startPage = pages.get("startPage");
        int endPage = pages.get("endPage");

        model.addAttribute("dto", allUserList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "admin/userlist";
    }

    @GetMapping("/userlist/search")
    public String searchUserList(@PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                 @RequestParam(value = "keyword") String keyword, Model model) {
        Page<AdminUserDto> usersByKeyword = adminService.findUsersByKeyword(keyword, pageable);

        int startPage = 0;
        int endPage = 0;

        if (!usersByKeyword.isEmpty()) {
            HashMap<String, Integer> pages = Pagination.calculatePage(usersByKeyword.getPageable().getPageNumber(), usersByKeyword.getTotalPages());
            startPage = pages.get("startPage");
            endPage = pages.get("endPage");
        }

        model.addAttribute("dto", usersByKeyword);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("keyword", keyword);

        return "admin/userlist";
    }

    @PutMapping("/userlist")
    public ResponseEntity<?> updateUserRole(Long userId, String role) {
        adminService.updateUserRole(userId, role);

        return ResponseEntity.ok("수정되었습니다.");
    }

    @DeleteMapping("/userlist")
    public ResponseEntity<?> deleteUser(Long userId) {
        adminService.deleteUser(userId);

        return ResponseEntity.ok("삭제되었습니다.");
    }

    //수량 관리
    @GetMapping("/stocklist")
    public String allStockList(@PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                               Model model) {
        Page<BookDto> allBook = adminService.findAllBook(pageable);

        HashMap<String, Integer> pages = Pagination.calculatePage(allBook.getPageable().getPageNumber(), allBook.getTotalPages());
        int startPage = pages.get("startPage");
        int endPage = pages.get("endPage");

        model.addAttribute("dto", allBook);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "admin/stocklist";
    }

    @GetMapping("/stocklist/search")
    public String searchStockList(@PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                  @RequestParam(value = "keyword") String keyword, Model model) {
        Page<BookDto> booksByKeyword = adminService.findBooksByKeyword(keyword, pageable);

        int startPage = 0;
        int endPage = 0;

        if (!booksByKeyword.isEmpty()) {
            HashMap<String, Integer> pages = Pagination.calculatePage(booksByKeyword.getPageable().getPageNumber(), booksByKeyword.getTotalPages());
            startPage = pages.get("startPage");
            endPage = pages.get("endPage");
        }

        model.addAttribute("dto", booksByKeyword);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("keyword", keyword);

        return "admin/stocklist";
    }

    @GetMapping("/stocklist/sold-out")
    public String searchStockList(@PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                  Model model) {
        Page<BookDto> booksByKeyword = adminService.findSoldOutBooks(pageable);

        int startPage = 0;
        int endPage = 0;

        if (!booksByKeyword.isEmpty()) {
            HashMap<String, Integer> pages = Pagination.calculatePage(booksByKeyword.getPageable().getPageNumber(), booksByKeyword.getTotalPages());
            startPage = pages.get("startPage");
            endPage = pages.get("endPage");
        }

        model.addAttribute("dto", booksByKeyword);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "admin/stocklist";
    }

    @PutMapping("/stocklist")
    public ResponseEntity<?> updateBookStock(Long bookId, Long stock) {
        adminService.updateBookStock(bookId, stock);

        return ResponseEntity.ok("수정되었습니다.");
    }

    @DeleteMapping("/stocklist")
    public ResponseEntity<?> deleteBook(Long bookId) {
        adminService.deleteBook(bookId);

        return ResponseEntity.ok("삭제되었습니다");
    }

    //책 관리
    @GetMapping("/booklist")
    public String allBooklist(@PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                              Model model) {
        Page<BookDto> allBook = adminService.findAllBook(pageable);

        HashMap<String, Integer> pages = Pagination.calculatePage(allBook.getPageable().getPageNumber(), allBook.getTotalPages());
        int startPage = pages.get("startPage");
        int endPage = pages.get("endPage");

        model.addAttribute("dto", allBook);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "admin/booklist";
    }

    @GetMapping("/booklist/search")
    public String searchBookList(@PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                 @RequestParam(value = "keyword") String keyword, Model model) {
        Page<BookDto> booksByKeyword = adminService.findBooksByKeyword(keyword, pageable);

        int startPage = 0;
        int endPage = 0;

        if (!booksByKeyword.isEmpty()) {
            HashMap<String, Integer> pages = Pagination.calculatePage(booksByKeyword.getPageable().getPageNumber(), booksByKeyword.getTotalPages());
            startPage = pages.get("startPage");
            endPage = pages.get("endPage");
        }

        model.addAttribute("dto", booksByKeyword);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("keyword", keyword);

        return "admin/booklist";
    }

    @GetMapping("/booklist/edit")
    public String getBookEditForm(Long bookId, Model model) {

        BookDto book = adminService.findBookById(bookId);

        model.addAttribute("book", book);

        return "admin/book-edit-form";
    }

    @PutMapping("/booklist/edit")
    public ResponseEntity<?> updateBook(BookDto bookDto, BindingResult result) {

        AdminUpdateBookValidator validator = new AdminUpdateBookValidator();
        validator.validate(bookDto, result);

        adminService.updateBook(bookDto);

        return ResponseEntity.ok("수정되었습니다.");
    }

    // 신간 등록
    @GetMapping("/book_registration")
    public String getBookRegistrationPage(Model model) {

        List<BookDto> books = new ArrayList<>();

        model.addAttribute("books", books);
        model.addAttribute("totalPage", 0);
        model.addAttribute("startPage", 0);
        model.addAttribute("endPage", 0);

        return "admin/book-registration";
    }

    @GetMapping("/book_registration/search")
    public String searchNewBook(String keyword, @RequestParam(defaultValue = "1") int page, Model model) {

        HashMap<String, ?> newBook = adminService.getNewBook(keyword, page);

        List<BookDto> books = (List<BookDto>) newBook.get("books");
        int totalPage = (int) newBook.get("totalPage");

        int startPage = 0;
        int endPage = 0;

        if (!books.isEmpty()) {
            HashMap<String, Integer> pages = Pagination.calculatePage(page - 1, totalPage);
            startPage = pages.get("startPage");
            endPage = pages.get("endPage");
        }

        model.addAttribute("books", books);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);

        return "admin/book-registration";
    }

    @PostMapping("/book_registration")
    public ResponseEntity<?> registrationNewBook(@RequestBody List<BookDto> books) {

        adminService.addNewBook(books);

        return ResponseEntity.ok("등록되었습니다.");
    }
    @GetMapping("/sync")
    public ResponseEntity<?> synchronizationDB() throws IOException, TasteException {

        List<SimpleReview> reviews = reviewService.getSimpleReviews();
        recommendBookService.GenerateRecommendBookList(reviews);

        return ResponseEntity.ok("추천 정보 동기화 완료");
    }

    @GetMapping("/sales")
    public String getSales(Model model) {

        return "admin/sales";
    }

    @GetMapping("/sales_category")
    public String getSalesCategory(Model model) {

        return "admin/sales-category";
    }

    @GetMapping("/sales_book")
    public String getSalesBook(@PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable,
                               Model model, String keyword) {

        Page<SalesDetailDto> result = salesService.getSalesByBook(keyword, pageable);

        int startPage = 0;
        int endPage = 0;

        if (!result.isEmpty()) {
            HashMap<String, Integer> pages = Pagination.calculatePage(result.getPageable().getPageNumber(), result.getTotalPages());
            startPage = pages.get("startPage");
            endPage = pages.get("endPage");
        }

        model.addAttribute("dto", result);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("keyword", keyword);

        return "admin/sales-book";
    }

    @GetMapping("/order")
    public String getOrders(@PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable,
                            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                            Model model) {

        if (date == null) {
            date = LocalDate.now();
        }

        Page<DailyOrderDto> result = orderService.getDailyOrders(date, pageable);

        int startPage = 0;
        int endPage = 0;

        if (!result.isEmpty()) {
            HashMap<String, Integer> pages = Pagination.calculatePage(result.getPageable().getPageNumber(), result.getTotalPages());
            startPage = pages.get("startPage");
            endPage = pages.get("endPage");
        }

        model.addAttribute("dto", result);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "admin/order";
    }

    @GetMapping("/order/{id}")
    public String getOrderDetail(@PathVariable Long id,  Model model) {

        OrderDetailDTO orderDetail = orderService.getOrderDetail(id);

        model.addAttribute("orderDetail", orderDetail);

        return "admin/order-detail";
    }

    //배송 관리
    @GetMapping("/delivery")
    public String getDelivery(@PageableDefault(page = 0, size = 5) Pageable pageable,
                              Model model){
        Page<DeliveryDto> deliveryDetail = orderService.getDelivery(pageable);

        HashMap<String, Integer> pages = Pagination.calculatePage(deliveryDetail.getPageable().getPageNumber(), deliveryDetail.getTotalPages());
        int startPage = pages.get("startPage");
        int endPage = pages.get("endPage");

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("dto", deliveryDetail);

        return "admin/delivery";
    }

    @GetMapping("/delivery/search")
    public String searchDelivery(@PageableDefault(page = 0, size = 5) Pageable pageable,
                                 String keyword,
                                 Model model){
        Page<DeliveryDto> deliveryDetail = orderService.getDeliveryByKeyword(keyword, pageable);

        HashMap<String, Integer> pages = Pagination.calculatePage(deliveryDetail.getPageable().getPageNumber(), deliveryDetail.getTotalPages());
        int startPage = pages.get("startPage");
        int endPage = pages.get("endPage");

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("dto", deliveryDetail);
        model.addAttribute("keyword", keyword);

        return "admin/delivery";
    }

    @PutMapping("/delivery")
    public ResponseEntity<?> updateDeliveryStatus(String deliveryId, String status){
        orderService.updateDeliveryStatus(deliveryId, status);

        return ResponseEntity.ok("배송 상태가 수정되었습니다.");
    }


    @GetMapping("/updateinfo")
    public String adminUpdateInfo(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {

        long userId= UserChecker.getUserId(userDetails);
        User user = userService.getUserByUserId(userId);

        model.addAttribute("user", user);

        return "admin/update-info";
    }

    @GetMapping("/updatepw")
    public String adminUpdatePw(Model model) {

        return "admin/update-password";
    }

    @GetMapping("/gemini-recommend-book")
    public String showGeminiRecommendBooks(Model model) {
        List<GeminiRecommendBookDto> todayRecommendBooks = geminiRecommendBookService.getTodayRecommendBooks();
        model.addAttribute("todayRecommendBooks", todayRecommendBooks);
        return "admin/gemini-recommend-book";
    }
}