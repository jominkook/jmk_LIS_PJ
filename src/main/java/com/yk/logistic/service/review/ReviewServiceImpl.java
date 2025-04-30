package com.yk.logistic.service.review;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.yk.logistic.domain.item.Item;
import com.yk.logistic.domain.member.Member;
import com.yk.logistic.domain.review.Review;
import com.yk.logistic.dto.review.ReviewRequest;
import com.yk.logistic.dto.review.ReviewResponse;
import com.yk.logistic.repository.item.ItemRepository;
import com.yk.logistic.repository.member.MemberRepository;
import com.yk.logistic.repository.review.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
	private final ReviewRepository reviewRepository;
	private final ItemRepository itemRepository;
	private final MemberRepository memberRepository;
	
	public Review addReview(Long itemId, ReviewRequest reviewRequest) {
	    // 아이템 조회
	    Item item = itemRepository.findById(itemId)
	            .orElseThrow(() -> new RuntimeException("Item with ID " + itemId + " not found"));

	

	    Member reviewee = memberRepository.findById(reviewRequest.getRevieweeId())
	            .orElseThrow(() -> new RuntimeException("Reviewee with ID " + reviewRequest.getRevieweeId() + " not found"));
	    

	    Review review = Review.builder()
	            .reviewee(reviewee)
	            .item(item)
	            .content(reviewRequest.getContent())
	            .rating(reviewRequest.getRating())
	            .build();

	    return reviewRepository.save(review);
	}
	
	public List<ReviewResponse> getReviewsByItemId(Long itemId) {
		
	    // 아이템 ID로 리뷰 목록 조회
	    List<Review> reviews = reviewRepository.findByItem_Id(itemId);

	    // 리뷰를 ReviewResponse DTO로 변환
	    return reviews.stream()
	            .map(review -> new ReviewResponse(
	                    review.getId(),                  // 리뷰 ID
	                    review.getItem().getTitle(),     // 아이템 이름
	                    review.getRating(),              // 평점
	                    review.getContent()              // 리뷰 내용
	            ))
	            .collect(Collectors.toList());
	}

}
