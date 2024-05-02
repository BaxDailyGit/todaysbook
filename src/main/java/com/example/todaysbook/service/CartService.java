package com.example.todaysbook.service;

import com.example.todaysbook.domain.dto.BookDto;
import com.example.todaysbook.domain.dto.CartRequestDto;
import com.example.todaysbook.domain.entity.Book;
import com.example.todaysbook.domain.entity.Cart;
import com.example.todaysbook.domain.entity.CartBook;
import com.example.todaysbook.domain.entity.User;
import com.example.todaysbook.repository.BookRepository;
import com.example.todaysbook.repository.CartBookRepository;
import com.example.todaysbook.repository.CartRepository;
import com.example.todaysbook.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CartService {

    private static final String USER_NOT_FOUND = "User not found";
    private static final String PRODUCT_NOT_FOUND = "Product not found";



    private final CartRepository cartRepository;
    private final CartBookRepository cartBookRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;


//

    @Transactional
    public void removeFromCart(long cartId, long bookId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if (cart != null) {
            CartBook cartBook = cartBookRepository.findByCartIdAndBookId(cartId, bookId);
            if (cartBook != null) {
                long bookCount = cartBook.getBookCount();
                long totalPrice = bookRepository.findById(bookId).map(book -> book.getPrice() * bookCount).orElse(0L);
                cartBookRepository.delete(cartBook);
//                cart.setTotalPrice(cart.getTotalPrice() - totalPrice);
                cartRepository.save(cart);
            }
        }
    }

    @Transactional
    public long addToCart(CartRequestDto requestDto) {
        // 로그 추가
        System.out.println("Adding book to cart...");

        // 장바구니에 담을 책 엔티티 조회
        Book book = bookRepository.findById(requestDto.getBookId())
                .orElseThrow(EntityNotFoundException::new);
        //예시로 아이디가 1인 사용자를 조회
        User user = userRepository.findById(1L);

        // 로그 추가
        System.out.println("Book retrieved: " + book.getTitle());

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
            savedCartBook.addCount(requestDto.getBookCount());
            cartBookRepository.save(savedCartBook);
            // 로그 추가
            System.out.println("Book quantity updated in cart.");
            return savedCartBook.getId();
        } else {
            // 장바구니에 새로운 도서 추가
            CartBook cartBook = CartBook.createCartBook(cart, book, requestDto.getBookCount());
            cartBookRepository.save(cartBook);
            // 로그 추가
            System.out.println("New book added to cart.");
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
            totalPrice += cartBook.getBook().getPrice() * cartBook.getBookCount();
        }
        return totalPrice;
    }

    // 총 적립 마일리지 계산 메소드 (임시)
    public int calculateTotalMileage(List<CartBook> cartBooks) {
        return 0; // 일단은 임시로 0으로 설정
    }



    private CartRequestDto entityToCartDto(Book book){
        return CartRequestDto.builder()
                .bookId(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .price(book.getPrice())
                .build();
    }



}




