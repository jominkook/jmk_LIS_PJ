package com.yk.logistic.service.review;

import java.util.List;

import com.yk.logistic.domain.review.Review;
import com.yk.logistic.dto.review.request.ReviewRequest;
import com.yk.logistic.dto.review.response.ReviewResponse;

public interface ReviewService {
	public Review addReview(Long itemId, ReviewRequest reviewRequest);
	
	public List<ReviewResponse> getReviewsByItemId(Long itemId);
}
