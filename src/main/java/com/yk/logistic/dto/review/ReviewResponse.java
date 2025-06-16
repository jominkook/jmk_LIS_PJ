package com.yk.logistic.dto.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor // 모든 필드를 포함하는 생성자
public class ReviewResponse {
	private Long id;         // 리뷰 ID
    private String itemName; // 아이템 이름
    private int rating;      // 평점
    private String content;  // 리뷰 내용
}
