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
    private Long sellerId;
    private String sellerName;
    private String sellerEmail;
    private String categoryName;
    private String parentCategoryName;
    private String imageUrl;
    private int startPrice; // 경매 시작가
    private String auctionEndTime; // 경매 종료 시간
    private String description; // 상품 설명 (추가)
    private String categoryFullName; // 카테고리 전체 이름 (추가)
    private Double latitude;
    private Double longitude;


    public ItemResDto(Long id, String title, Address origin, int price,  Long sellerId, String sellerName,
                      String sellerEmail, String categoryName, String parentCategoryName, String imageUrl,
                      int startPrice, String auctionEndTime, String description, String categoryFullName, Double latitude, Double longitude) {
        this.id = id;
        this.title = title;
        this.origin = origin;
        this.price = price;
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.sellerEmail = sellerEmail;
        this.categoryName = categoryName;
        this.parentCategoryName = parentCategoryName;
        this.imageUrl = imageUrl;
        this.startPrice = startPrice;
        this.auctionEndTime = auctionEndTime;
        this.description = description; // 상품 설명 추가
        this.categoryFullName = categoryFullName; // 카테고리 전체 이름 추가
        this.latitude = latitude;
        this.longitude = longitude;
    }
}