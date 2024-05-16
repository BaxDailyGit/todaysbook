package com.example.todaysbook.controller;

import com.example.todaysbook.domain.dto.CartRequestDto;
import com.example.todaysbook.domain.dto.CustomUserDetails;
import com.example.todaysbook.domain.dto.PaymentBookInfoDto;
import com.example.todaysbook.domain.entity.Book;
import com.example.todaysbook.domain.entity.Cart;

import com.example.todaysbook.domain.entity.CartBook;
import com.example.todaysbook.domain.entity.Role;
import com.example.todaysbook.service.CartService;
import com.example.todaysbook.util.UserChecker;
import com.example.todaysbook.validate.CartValidator;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String showMyCartList(Model model, @AuthenticationPrincipal CustomUserDetails userDetails, HttpServletRequest req) {
        // userId로 사용자의 장바구니 목록 조회


        //0503수정
        long userId = userDetails.getUserId();
        List<CartBook> cartBooks = cartService.findCartBooksByUserId(userId);

        Role userRole = Role.valueOf(userDetails.getAuthorities().iterator().next().getAuthority());

        // 총 주문 금액 및 적립 마일리지 계산
        int totalPrice = cartService.calculateTotalPrice(cartBooks);
        int totalMileage = cartService.calculateTotalMileage(cartBooks, userRole);
        double mileageRate = cartService.individualMileage(userRole);


        // 모델에 데이터 추가
        model.addAttribute("cartBooks", cartBooks);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("totalMileage", totalMileage);
        model.addAttribute("membershipLevel", userRole.getName()); // 등급 정보 추가
        model.addAttribute("mileageRate", mileageRate);
        return "cart/list"; // 템플릿 이름 리턴
    }

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody CartRequestDto requestDto, BindingResult result,
                                       @AuthenticationPrincipal CustomUserDetails userDetails) {

        long userId = UserChecker.getUserId(userDetails);
        requestDto.setUserId(userId);

        CartValidator validator = new CartValidator();
        validator.validate(requestDto, result);

        long cartBookId = cartService.addToCart(requestDto, userDetails);
        return ResponseEntity.ok(cartBookId);
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

////0503~ 구현중

    @PutMapping("/increase-quantity/{cartBookId}")
    public ResponseEntity<?> increaseQuantity(@PathVariable Long cartBookId) {
        try {
            cartService.increaseCartBookQuantity(cartBookId);
            return ResponseEntity.ok().body(Map.of("success", true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("success", false));
        }
    }

    @PutMapping("/decrease-quantity/{cartBookId}")
    public ResponseEntity<?> decreaseQuantity(@PathVariable Long cartBookId) {
        try {
            cartService.decreaseCartBookQuantity(cartBookId);
            return ResponseEntity.ok().body(Map.of("success", true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("success", false));
        }
    }
}