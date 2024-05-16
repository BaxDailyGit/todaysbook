package com.example.todaysbook.validate;

import com.example.todaysbook.domain.dto.CartRequestDto;
import com.example.todaysbook.exception.UnauthorizedUserException;
import com.example.todaysbook.exception.cart.InvalidQuantityException;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class CartValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

        CartRequestDto requestDto = (CartRequestDto) target;

        if(isUnauthorized(requestDto.getUserId())) {

            throw new UnauthorizedUserException();
        }

        if(isCountLessThanMinimum(requestDto.getCount())) {

            throw new InvalidQuantityException();
        }
    }

    private boolean isUnauthorized(Long userId) {

        return userId == 0;
    }

    private boolean isCountLessThanMinimum(Long count) {

        return count < 1;
    }
}
