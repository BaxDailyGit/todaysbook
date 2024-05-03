package com.example.todaysbook.controller;

import com.example.todaysbook.domain.dto.CartRequestDto;
import com.example.todaysbook.domain.entity.Book;
import com.example.todaysbook.domain.entity.Cart;

import com.example.todaysbook.domain.entity.CartBook;
import com.example.todaysbook.service.CartService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping("/list")
    public String showMyCartList(Model model) {
        // userId가 1인 사용자의 장바구니 목록 조회
        List<CartBook> cartBooks = cartService.findCartBooksByUserId(1L);
        // 총 주문 금액 및 적립 마일리지 계산
        int totalPrice = cartService.calculateTotalPrice(cartBooks);
        int totalMileage = cartService.calculateTotalMileage(cartBooks);

        // 모델에 데이터 추가
        model.addAttribute("cartBooks", cartBooks);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("totalMileage", totalMileage);

        return "cart/list"; // 템플릿 이름 리턴
    }

    @PostMapping("/add")
    public ResponseEntity<Long> addToCart(@RequestBody CartRequestDto requestDto) {
        // 강제로 userId를 1로 설정하여 테스트
        requestDto.setUserId(1L);

        try {
            long cartBookId = cartService.addToCart(requestDto);
            return ResponseEntity.ok(cartBookId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 서버 측 컨트롤러
    @PostMapping("/delete-unselected")
    public ResponseEntity<?> deleteSelectedCartItems(@RequestBody List<Long> selectedIds) {
        try {
            cartService.deleteSelectedCartItems(selectedIds);
            return ResponseEntity.ok().body(Map.of("success", true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("success", false));
        }
    }
}
