package com.yk.logistic.service.review;

import java.util.List;

import com.yk.logistic.domain.review.Review;
import com.yk.logistic.dto.review.ReviewRequest;
import com.yk.logistic.dto.review.ReviewResponse;

public interface ReviewService {
	public Review addReview(Long itemId, ReviewRequest reviewRequest);
	
	public List<ReviewResponse> getReviewsByItemId(Long itemId);
}
