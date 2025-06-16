package com.yk.logistic.controller.review;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.yk.logistic.domain.review.Review;
import com.yk.logistic.dto.review.ReviewRequest;
import com.yk.logistic.service.review.ReviewService;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ReviewApiController {
	private final ReviewService reviewService;
	
	// 리뷰 등록
    @PostMapping("/{itemId}/reviews")
    public ResponseEntity<Review> addReview(@PathVariable Long itemId, @RequestBody ReviewRequest reviewRequest) {
//    	System.out.println("리뷰아이템아이디:"+itemId);
//    	System.out.println("reviewRequest.revieweeId: " + reviewRequest.getRevieweeId());
//    	System.out.println("reviewRequest.rating: " + reviewRequest.getRating());
//      System.out.println("reviewRequest.content: " + reviewRequest.getContent());
        Review savedReview = reviewService.addReview(itemId, reviewRequest);
        return ResponseEntity.ok(savedReview);
    }
	
//    // 특정 아이템의 리뷰 조회
//    @GetMapping("/{itemId}/reviews")
//    public ResponseEntity<List<ReviewResponse>> getReviewsByItemId(@PathVariable Long itemId) {
//        List<ReviewResponse> reviews = reviewService.getReviewsByItemId(itemId);
//        return ResponseEntity.ok(reviews);
//    }
}
