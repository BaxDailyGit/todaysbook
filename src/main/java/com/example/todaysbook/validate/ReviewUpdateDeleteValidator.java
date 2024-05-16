package com.example.todaysbook.validate;

import com.example.todaysbook.exception.UnauthorizedUserException;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Map;

public class ReviewUpdateDeleteValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

        Map<String, Long> map = (Map<String, Long>) target;

        if(isNotReviewOwner(map.get("userId") , map.get("reviewOwnerId"))) {

            throw new UnauthorizedUserException();
        }
    }

    private boolean isNotReviewOwner(Long userId, Long reviewOwnerId) {

        return userId != reviewOwnerId;
    }
}
