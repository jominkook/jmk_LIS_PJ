package com.yk.logistic.dto.item.request;

import com.yk.logistic.domain.address.Address;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveItemReqDto {
	private String title; // 상품 제목 (필드 이름 변경)
    private Address origin; // 상품 위치 (도로명, 도시, 우편번호)
    private int price; // 상품 가격
    private Long categoryId; // 카테고리 ID
    private String imageUrls; // 사진 경로 추가
    private String status; // 상태값 추가
    
    // 모든 필드를 초기화하는 생성자 추가
    public SaveItemReqDto(String title, String street, String city, String zipCode, int price, Long categoryId, String imageUrls,String status) {
        this.title = title;
        this.origin = new Address(street, city, zipCode); // Address 객체 생성
        this.price = price;
        this.categoryId = categoryId;
        this.imageUrls = imageUrls;
        this.status = status;
    }
    
    
}