package com.example.todaysbook.validate;

import com.example.todaysbook.constant.Constant;
import com.example.todaysbook.domain.dto.ReviewRequestDto;
import com.example.todaysbook.exception.review.ContentEmptyException;
import com.example.todaysbook.exception.review.ContentLengthOverException;
import com.example.todaysbook.exception.UnauthorizedUserException;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ReviewCreateValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ReviewRequestDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ReviewRequestDto requestDto = (ReviewRequestDto) target;

        if(isEmpty(requestDto.getContent())) {

            throw new ContentEmptyException();
        }

        if(isContentLengthOver(requestDto.getContent())) {

            throw new ContentLengthOverException();
        }

        if(isUnauthorized(requestDto.getUserId())) {

            throw new UnauthorizedUserException();
        }
    }

    private boolean isEmpty(String content) {

        return content.isEmpty();
    }

    private boolean isContentLengthOver(String content) {

        return content.length() > Constant.REVIEW_CONTENT_MAX_LENGTH;
    }

    private boolean isUnauthorized(Long userId) {

        return userId == 0;
    }
}
