package com.yk.logistic.dto.item.request;

import com.yk.logistic.domain.address.Address;
import com.yk.logistic.domain.member.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveItemReqDto {
    private String title;
    private Address origin;
    private int price;
    private Long categoryId;
    private String imageUrls;
    private String status;
    private int startPrice;
    private String auctionEndTime;
    private String description;
    private Member seller;
    private Double latitude;
    private Double longitude;

    public SaveItemReqDto(String title, String street, String city, String zipCode, int price, Long categoryId,
                          String imageUrls, String status, int startPrice, String auctionEndTime,
                          Double latitude, Double longitude) {
        this.title = title;
        this.origin = new Address(street, city, zipCode);
        this.price = price;
        this.categoryId = categoryId;
        this.imageUrls = imageUrls;
        this.status = status;
        this.startPrice = startPrice;
        this.auctionEndTime = auctionEndTime;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}