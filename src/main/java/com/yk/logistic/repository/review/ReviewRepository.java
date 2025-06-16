package com.yk.logistic.repository.review;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yk.logistic.domain.review.Review;

public interface ReviewRepository extends JpaRepository<Review,Long> {
	List<Review> findByItem_Id(Long itemId); // 아이템 ID를 기준으로 리뷰 조회
}
