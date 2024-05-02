package com.example.todaysbook.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity

public class CartBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart; // Cart 엔티티와의 관계

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book; // Book 엔티티와의 관계

    private long bookCount;



    public static CartBook createCartBook(Cart cart,Book book,long bookCount){
        CartBook cartBook = new CartBook();
        cartBook.setCart(cart);
        cartBook.setBook(book);
        cartBook.setBookCount(bookCount);
        return cartBook;
    }

    public void addCount(long bookCount) {
        this.bookCount += bookCount;
    }

}
