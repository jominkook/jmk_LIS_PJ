package com.yk.logistic.dto.review;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequest {
    private Long revieweeId; // 리뷰 대상자 ID
    private String content;  // 리뷰 내용
    private int rating;      // 평점
}