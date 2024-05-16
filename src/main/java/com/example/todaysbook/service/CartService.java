package com.example.todaysbook.service;

import com.example.todaysbook.domain.dto.CartRequestDto;
import com.example.todaysbook.domain.dto.CustomUserDetails;
import com.example.todaysbook.domain.dto.PaymentBookInfoDto;
import com.example.todaysbook.domain.entity.*;
import com.example.todaysbook.repository.BookRepository;
import com.example.todaysbook.repository.CartBookRepository;
import com.example.todaysbook.repository.CartRepository;
import com.example.todaysbook.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CartService {

    private static final String USER_NOT_FOUND = "User not found";
    private static final String PRODUCT_NOT_FOUND = "Product not found";



    private final CartRepository cartRepository;
    private final CartBookRepository cartBookRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;




    @Transactional
    public long addToCart(CartRequestDto requestDto,@AuthenticationPrincipal CustomUserDetails userDetails) {
        // 현재 로그인한 사용자의 아이디를 가져옴
        // userId로 수정실험
        //0503수정
        long userId = userDetails.getUserId();
        requestDto.setUserId(userId);
        // 장바구니에 담을 책 엔티티 조회
        Book book = bookRepository.findById(requestDto.getBookId())
                .orElseThrow(EntityNotFoundException::new);
        //userId를 통해 사용자를 조회
        User user = userRepository.findById(requestDto.getUserId());

        // 장바구니 조회
        Cart cart = cartRepository.findByUserId(user.getId());

        // 장바구니가 없으면 새로 생성
        if (cart == null) {
            cart = Cart.createCart(user);
            cartRepository.save(cart);
        }

        // 장바구니에 해당 도서가 이미 존재하는지 확인
        CartBook savedCartBook = cartBookRepository.findByCartIdAndBookId(cart.getId(), book.getId());

        // 이미 존재하는 도서라면 수량만 증가
        if (savedCartBook != null) {
            savedCartBook.addCount(requestDto.getCount());
            cartBookRepository.save(savedCartBook);

            return savedCartBook.getId();
        } else {
            // 장바구니에 새로운 도서 추가
            CartBook cartBook = CartBook.createCartBook(cart, book, requestDto.getCount());
            cartBookRepository.save(cartBook);

            return cartBook.getId();
        }
    }

    @Transactional
    public List<CartBook> findCartBooksByUserId(Long userId) {
        return cartBookRepository.findCartBooksByUserId(userId);
    }

    @Transactional
    public void deleteSelectedCartItems(List<Long> cartBookIds) {
        for (Long cartBookId : cartBookIds) {
            // 카트 상품 ID를 사용하여 해당 상품을 찾고 삭제합니다.
            cartBookRepository.deleteById(cartBookId);
        }
    }

    // 총 주문 금액 계산 메소드
    public int calculateTotalPrice(List<CartBook> cartBooks) {
        int totalPrice = 0;
        for (CartBook cartBook : cartBooks) {
            totalPrice += cartBook.getBook().getPrice() * cartBook.getCount();
        }
        return totalPrice;
    }

    // 각 등급에 따른 적립율 설정
    private static final double BRONZE_RATE = 0.03;
    private static final double SILVER_RATE = 0.05;
    private static final double GOLD_RATE = 0.07;
    private static final double DIAMOND_RATE = 0.10;


    @Transactional
    public double individualMileage(Role userRole){
        double mileageRate;
        switch (userRole) {
            case ROLE_BRONZE:
                mileageRate = BRONZE_RATE;
                break;
            case ROLE_SILVER:
                mileageRate = SILVER_RATE;
                break;
            case ROLE_GOLD:
                mileageRate = GOLD_RATE;
                break;
            case ROLE_DIAMOND:
                mileageRate = DIAMOND_RATE;
                break;
            default:
                mileageRate = BRONZE_RATE;
                break;
        }
        return mileageRate;
    }
    // 총 적립 마일리지 계산 메서드
    @Transactional
    public int calculateTotalMileage(List<CartBook> cartBooks, Role userRole) {
        int totalPrice = calculateTotalPrice(cartBooks);
        double mileageRate;
        switch (userRole) {
            case ROLE_BRONZE:
                mileageRate = BRONZE_RATE;
                break;
            case ROLE_SILVER:
                mileageRate = SILVER_RATE;
                break;
            case ROLE_GOLD:
                mileageRate = GOLD_RATE;
                break;
            case ROLE_DIAMOND:
                mileageRate = DIAMOND_RATE;
                break;
            default:
                mileageRate = BRONZE_RATE;
                break;
        }
        return (int) (totalPrice * mileageRate);
    }

    ///0503~구현중
    @Transactional
    public void increaseCartBookQuantity(Long cartBookId) {
        CartBook cartBook = cartBookRepository.findById(cartBookId)
                .orElseThrow(EntityNotFoundException::new);
        cartBook.addCount(1); // 수량 증가
        cartBookRepository.save(cartBook);
    }

    @Transactional
    public void decreaseCartBookQuantity(Long cartBookId) {
        CartBook cartBook = cartBookRepository.findById(cartBookId)
                .orElseThrow(EntityNotFoundException::new);
        if (cartBook.getCount() > 1) {
            cartBook.addCount(-1); // 수량 감소
            cartBookRepository.save(cartBook);
        } else {
            // 수량이 1인 경우 삭제
            cartBookRepository.deleteById(cartBookId);
        }
    }


}




