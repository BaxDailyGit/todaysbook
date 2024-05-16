package com.example.todaysbook.exception;

import com.example.todaysbook.exception.admin.AuthorLengthOverException;
import com.example.todaysbook.exception.admin.DescriptionLengthOverException;
import com.example.todaysbook.exception.admin.EmptyAuthorException;
import com.example.todaysbook.exception.admin.EmptyPriceException;
import com.example.todaysbook.exception.admin.EmptyPublisherException;
import com.example.todaysbook.exception.admin.PublisherLengthOverException;
import com.example.todaysbook.exception.book.DuplicateBookException;
import com.example.todaysbook.exception.bookMark.AlreadyBookmarkedException;
import com.example.todaysbook.exception.bookMark.NotBookmarkedYetException;
import com.example.todaysbook.exception.cart.InvalidQuantityException;
import com.example.todaysbook.exception.delivery.DeliveryNotFoundException;
import com.example.todaysbook.exception.order.OrderNotFoundException;
import com.example.todaysbook.exception.recommendList.BookNotFoundException;
import com.example.todaysbook.exception.recommendList.EmptyTitleException;
import com.example.todaysbook.exception.recommendList.RecommendListLengthOverException;
import com.example.todaysbook.exception.recommendList.RecommendListNotFoundException;
import com.example.todaysbook.exception.recommendList.TitleLengthOverException;
import com.example.todaysbook.exception.review.ContentEmptyException;
import com.example.todaysbook.exception.review.ContentLengthOverException;
import com.example.todaysbook.exception.user.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    //유저 관련 예외처리
    @ExceptionHandler(NotLoggedInException.class)
    public ResponseEntity<String> NotLoggedInException(NotLoggedInException e){
        return new ResponseEntity<>("로그인을 해주세요.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserValidateException.class)
    public ResponseEntity<String> UserValidateException(UserValidateException e){
        return new ResponseEntity<>("인증되지 않은 유저입니다.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> UserNotFoundException(UserNotFoundException e){
        return new ResponseEntity<>("해당 유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmptyAddressException.class)
    public ResponseEntity<String> EmptyAddressException(EmptyAddressException ex) {
        return new ResponseEntity<>("주소를 입력해주세요.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyEmailException.class)
    public ResponseEntity<String> EmptyEmailException(EmptyEmailException ex) {
        return new ResponseEntity<>("이메일을 입력해주세요.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyNameException.class)
    public ResponseEntity<String> EmptyNameException(EmptyNameException ex) {
        return new ResponseEntity<>("이름을 입력해주세요.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyNicknameException.class)
    public ResponseEntity<String> EmptyNicknameException(EmptyNicknameException ex) {
        return new ResponseEntity<>("닉네임을 입력해주세요.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyPasswordException.class)
    public ResponseEntity<String> EmptyPasswordException(EmptyPasswordException ex) {
        return new ResponseEntity<>("비밀번호를 입력해주세요.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyZipcodeException.class)
    public ResponseEntity<String> EmptyZipcodeException(EmptyZipcodeException ex) {
        return new ResponseEntity<>("우편번호를 입력해주세요.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NicknameLengthOverException.class)
    public ResponseEntity<String> NicknameLengthOverException(NicknameLengthOverException ex) {
        return new ResponseEntity<>("닉네임의 길이가 초과되었습니다.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NicknameLengthUnderException.class)
    public ResponseEntity<String> NicknameLengthUnderException(NicknameLengthUnderException ex) {
        return new ResponseEntity<>("닉네임의 길이가 부족합니다.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotSamePasswordException.class)
    public ResponseEntity<String> NotSamePasswordException(NotSamePasswordException ex) {
        return new ResponseEntity<>("입력된 비밀번호가 같지 않습니다.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WrongEmailPatternException.class)
    public ResponseEntity<String> WrongEmailPatternException(WrongEmailPatternException ex) {
        return new ResponseEntity<>("이메일의 형식이 올바르지 않습니다.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WrongPasswordPatternException.class)
    public ResponseEntity<String> WrongPasswordPatternException(WrongPasswordPatternException ex) {
        return new ResponseEntity<>("비밀번호의 형식이 올바르지 않습니다.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WrongZipcodePatternException.class)
    public ResponseEntity<String> ZipcodeNotANumberException(WrongZipcodePatternException ex) {
        return new ResponseEntity<>("우편번호의 형식이 올바르지 않습니다.", HttpStatus.BAD_REQUEST);
    }
    //책 관련 예외처리
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<String> BookNotFoundException(BookNotFoundException e){
        return new ResponseEntity<>("해당 책을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateBookException.class)
    public ResponseEntity<String> DuplicateBookException(DuplicateBookException e){
        return new ResponseEntity<>("이미 등록된 책이 있습니다.", HttpStatus.BAD_REQUEST);
    }

    //추천 리스트 예외처리
    @ExceptionHandler(RecommendListNotFoundException.class)
    public ResponseEntity<String> RecommendListNotFoundException(RecommendListNotFoundException e){
        return new ResponseEntity<>("리스트를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TitleLengthOverException.class)
    public ResponseEntity<String> TitleLengthOverException(TitleLengthOverException e){
        return new ResponseEntity<>("제목 길이가 초과하였습니다.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyTitleException.class)
    public ResponseEntity<String> EmptyTitleException(EmptyTitleException e){
        return new ResponseEntity<>("제목을 입력해주세요.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RecommendListLengthOverException.class)
    public ResponseEntity<String> RecommendListLengthOverException(RecommendListLengthOverException e){
        return new ResponseEntity<>("추천 리스트는 10개 이하로 구성해주세요.", HttpStatus.BAD_REQUEST);
    }

    //북마크 관련 예외처리
    @ExceptionHandler(AlreadyBookmarkedException.class)
    public ResponseEntity<String> AlreadyBookmarkedException(AlreadyBookmarkedException ex) {
        return new ResponseEntity<>("해당 게시물은 이미 북마크로 등록이 되어있습니다.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotBookmarkedYetException.class)
    public ResponseEntity<String> NotBookmarkedYetException(NotBookmarkedYetException ex) {
        return new ResponseEntity<>("해당 게시물은 이미 북마크로 등록이 되어있지 않습니다.",HttpStatus.BAD_REQUEST);
    }


    //관리자 관련 에러처리
    @ExceptionHandler(AuthorLengthOverException.class)
    public ResponseEntity<String> AuthorLengthOverException(AuthorLengthOverException ex) {
        return new ResponseEntity<>("작가 길이가 초과되었습니다.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyAuthorException.class)
    public ResponseEntity<String> EmptyAuthorException(EmptyAuthorException ex) {
        return new ResponseEntity<>("작가 이름을 입력해주세요.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PublisherLengthOverException.class)
    public ResponseEntity<String> PublisherLengthOverException(PublisherLengthOverException ex) {
        return new ResponseEntity<>("출판사 길이가 초과되었습니다.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyPublisherException.class)
    public ResponseEntity<String> EmptyPublisherException(EmptyPublisherException ex) {
        return new ResponseEntity<>("출판사를 입력해주세요.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DescriptionLengthOverException.class)
    public ResponseEntity<String> DescriptionLengthOverException(DescriptionLengthOverException ex) {
        return new ResponseEntity<>("책 설명의 길이가 초과되었습니다.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyPriceException.class)
    public ResponseEntity<String> EmptyPriceException(EmptyPriceException ex) {
        return new ResponseEntity<>("책 가격을 입력해주세요.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedUserException.class)
    public ResponseEntity<String> UnauthorizedUserOfUser(UnauthorizedUserException ex) {
        return new ResponseEntity<>("로그인이 필요한 기능입니다.",HttpStatus.valueOf(401));
    }

    @ExceptionHandler(ContentEmptyException.class)
    public ResponseEntity<String> ReviewContentEmptyException(ContentEmptyException ex) {
        return new ResponseEntity<>("리뷰 내용을 입력해주세요.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ContentLengthOverException.class)
    public ResponseEntity<String> ReviewContentLengthOverException(ContentLengthOverException ex) {
        return new ResponseEntity<>("리뷰 내용의 길이가 초과되었습니다.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidQuantityException.class)
    public ResponseEntity<String> CartCountException(InvalidQuantityException ex) {
        return new ResponseEntity<>("장바구니에 담을 수 있는 최소 수량은 1입니다.", HttpStatus.BAD_REQUEST);
    }

    //배송 관련 예외처리
    @ExceptionHandler(DeliveryNotFoundException.class)
    public ResponseEntity<String> DeliveryNotFoundException(DeliveryNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

    //주문 관련 예외처리
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<String> OrderNotFoundException(OrderNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

}
