package com.yk.logistic.dto.item.response;

import com.yk.logistic.domain.address.Address;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemResDto {
    private Long id; // 상품 ID
    private String title; // 상품 제목
    private Address origin; // 상품 위치
    private int price; // 상품 가격
    private String status; // 거래 상태
    private String sellerName; // 판매자 이름
    private String categoryName; // 카테고리 이름
    private String parentCategoryName; // 부모 카테고리 이름
    
 
}