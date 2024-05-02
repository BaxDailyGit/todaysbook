package com.example.todaysbook.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long userId;



    public static Cart createCart(User user){
        Cart cart = new Cart();
        cart.setUserId(user.getId());
        return cart;

    }

}
