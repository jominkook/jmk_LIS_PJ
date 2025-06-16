package com.yk.logistic.dto.item.response;

import com.yk.logistic.domain.address.Address;
import lombok.Getter;

@Getter
public class ItemResDto {
    private Long id;
    private String title;
    private Address origin;
    private int price;
    private String status;
    private Long sellerId; // 판매자 ID
    private String sellerName; // 판매자 이름
    private String sellerEmail; // 판매자 이메일
    private String categoryName;
    private String parentCategoryName;
    private String imageUrl; // 이미지 URL 추가

    // 생성자
    public ItemResDto(Long id, String title, Address origin, int price, String status, 
            Long sellerId, String sellerName, String sellerEmail, 
            String categoryName, String parentCategoryName, String imageUrl) { // 이미지 URL 추가
        this.id = id;
        this.title = title;
        this.origin = origin;
        this.price = price;
        this.status = status;
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.sellerEmail = sellerEmail;
        this.categoryName = categoryName;
        this.parentCategoryName = parentCategoryName;
        this.imageUrl = imageUrl; // 이미지 URL 설정
    }
}