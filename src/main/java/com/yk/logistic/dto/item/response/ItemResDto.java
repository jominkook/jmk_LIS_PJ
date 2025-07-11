package com.yk.logistic.dto.item.response;

import com.yk.logistic.domain.address.Address;
import com.yk.logistic.dto.auction.response.AuctionResDto;
import lombok.Getter;

@Getter
public class ItemResDto {
    private Long id;
    private String title;
    private Address origin;
    private int price;
    private Long sellerId;
    private String sellerName;
    private String sellerEmail;
    private String categoryName;
    private String parentCategoryName;
    private String imageUrl;
    private int startPrice;
    private String auctionEndTime;
    private String description;
    private String categoryFullName;
    private Double latitude;
    private Double longitude;
    private String status; // 경매 상태 추가
    private AuctionResDto auction;

    public ItemResDto(Long id, String title, Address origin, int price, Long sellerId, String sellerName,
                      String sellerEmail, String categoryName, String parentCategoryName, String imageUrl,
                      int startPrice, String auctionEndTime, String description, String categoryFullName,
                      Double latitude, Double longitude, String status, AuctionResDto auction) { // status 추가
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
        this.description = description;
        this.categoryFullName = categoryFullName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status; // 경매 상태
        this.auction = auction;
    }
}