package com.yk.logistic.domain.item;

import java.util.ArrayList;
import java.util.List;

import com.yk.logistic.domain.address.Address;
import com.yk.logistic.domain.auction.Auction;
import com.yk.logistic.domain.category.Category;
import com.yk.logistic.domain.member.Member;
import com.yk.logistic.domain.review.Review;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; // 상품 제목

    @Embedded
    private Address origin; // 상품 위치 (도로명, 도시, 우편번호)

    private String description; // 상품 설명

    private int price; // 상품 가격

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Member seller; // 판매자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category; // 카테고리

    @OneToOne(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Auction auction;

    @OneToMany(mappedBy = "item", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>(); // 아이템에 대한 리뷰 리스트

    @OneToMany(mappedBy = "item", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true , fetch = FetchType.LAZY)
    private List<ItemImage> images = new ArrayList<>(); // 이미지 리스트

    // 위치기반 서비스 엔티티
    private Double latitude;
    private Double longitude;

    // 상태 필드 추가 (기본값: REGISTERED)
    private String status;

    @Builder
    public Item(String title, Address origin, String description, int price, Member seller, Category category, Double latitude, Double longitude, String status) {
        this.title = title;
        this.origin = origin;
        this.description = description;
        this.price = price;
        this.seller = seller;
        this.category = category;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = (status != null) ? status : "REGISTERED"; // 기본값 처리
    }

    public void updateItem(
            String title,
            Address origin,
            String description,
            int price,
            Category category,
            Double latitude,
            Double longitude
    ) {
        this.title = title;
        this.origin = origin;
        this.description = description;
        this.price = price;
        this.category = category;
        this.latitude = latitude;
        this.longitude = longitude;
        // images는 서비스에서 관리
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public void startAuction() {
        this.status = "ACTIVE";
    }
}