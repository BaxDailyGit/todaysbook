package com.example.todaysbook.controller;

import com.example.todaysbook.domain.dto.CustomUserDetails;
import com.example.todaysbook.domain.dto.Review;
import com.example.todaysbook.domain.dto.ReviewRequestDto;
import com.example.todaysbook.service.ReviewService;
import com.example.todaysbook.util.UserChecker;
import com.example.todaysbook.validate.ReviewCreateValidator;
import com.example.todaysbook.validate.ReviewUpdateDeleteValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("")
    public String getReviews(@RequestParam(value = "bookId") long bookId,
                             @RequestParam(value = "orderBy") String orderBy,
                             @AuthenticationPrincipal CustomUserDetails userDetails,
                             Model model) {

        long userId = UserChecker.getUserId(userDetails);

        List<Review> reviews =
                reviewService.getReviews(bookId, userId, orderBy);
        model.addAttribute("reviews", reviews);
        model.addAttribute("userId", userId);

        return "book/review";
    }

    @PostMapping("/add")
    public String addNewReview(@RequestBody ReviewRequestDto requestDto,
                               @AuthenticationPrincipal CustomUserDetails userDetails,
                               Model model, BindingResult result) {

        long userId = UserChecker.getUserId(userDetails);
        String orderBy = "latest";
        requestDto.setUserId(userId);

        ReviewCreateValidator validator = new ReviewCreateValidator();
        validator.validate(requestDto, result);

        reviewService.addReview(requestDto);

        List<Review> reviews =
                reviewService.getReviews(requestDto.getBookId(), requestDto.getUserId(), orderBy);
        model.addAttribute("reviews", reviews);
        model.addAttribute("userId", userId);

        return "book/review";
    }

    @DeleteMapping("/delete")
    public String deleteReview(@RequestBody ReviewRequestDto requestDto,
                               @AuthenticationPrincipal CustomUserDetails userDetails,
                               Model model, BindingResult result) {

        long userId = UserChecker.getUserId(userDetails);
        String orderBy = "latest";

        Map<String, Long> map = new HashMap<>();
        map.put("userId", userId);
        map.put("reviewOwnerId", reviewService.getReviewOwnerId(requestDto.getReviewId()));

        ReviewUpdateDeleteValidator validator = new ReviewUpdateDeleteValidator();
        validator.validate(map, result);

        reviewService.deleteReview(requestDto.getReviewId());

        List<Review> reviews =
                reviewService.getReviews(requestDto.getBookId(), userId, orderBy);
        model.addAttribute("reviews", reviews);
        model.addAttribute("userId", userId);

        return "book/review";
    }

    @PutMapping("/update")
    public String updateReview(@RequestBody ReviewRequestDto requestDto,
                               @AuthenticationPrincipal CustomUserDetails userDetails,
                               Model model, BindingResult result) {

        long userId = userDetails.getUserId();
        String orderBy = "latest";

        Map<String, Long> map = new HashMap<>();
        map.put("userId", userId);
        map.put("reviewOwnerId", reviewService.getReviewOwnerId(requestDto.getReviewId()));

        ReviewUpdateDeleteValidator validator = new ReviewUpdateDeleteValidator();
        validator.validate(map, result);
        reviewService.updateReview(requestDto);

        List<Review> reviews =
                reviewService.getReviews(requestDto.getBookId(), requestDto.getUserId(), orderBy);

        model.addAttribute("reviews", reviews);
        model.addAttribute("userId", userId);

        return "book/review";
    }

    @PostMapping("/add_like")
    public ResponseEntity<?> addLikeReview(@AuthenticationPrincipal CustomUserDetails userDetails,
                                           @RequestParam(value = "reviewId") long reviewId) {

        try {

            long userId = userDetails.getUserId();

            return ResponseEntity.ok(reviewService.addLikeReview(userId, reviewId));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/add_dislike")
    public ResponseEntity<?> addDislikeReview(@AuthenticationPrincipal CustomUserDetails userDetails,
                                           @RequestParam(value = "reviewId") long reviewId) {

        try {

            long userId = userDetails.getUserId();

            return ResponseEntity.ok(reviewService.addDislikeReview(userId, reviewId));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete_like")
    public ResponseEntity<?> deleteLikeReview(@AuthenticationPrincipal CustomUserDetails userDetails,
                                              @RequestParam(value = "reviewId") long reviewId) {

        try {

            long userId = userDetails.getUserId();

            return ResponseEntity.ok(reviewService.deleteLikeReview(userId, reviewId));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete_dislike")
    public ResponseEntity<?> deleteDislikeReview(@AuthenticationPrincipal CustomUserDetails userDetails,
                                              @RequestParam(value = "reviewId") long reviewId) {

        try {

            long userId = userDetails.getUserId();

            return ResponseEntity.ok(reviewService.deleteDislikeReview(userId, reviewId));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
