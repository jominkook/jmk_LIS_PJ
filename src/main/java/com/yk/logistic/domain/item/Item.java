package com.yk.logistic.domain.item;

import java.util.ArrayList;
import java.util.List;

import com.yk.logistic.domain.address.Address;
import com.yk.logistic.domain.category.Category;
import com.yk.logistic.domain.member.Member;
import com.yk.logistic.domain.review.Review;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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

    private int price; // 상품 가격

    @Enumerated(EnumType.STRING)
    private ItemStatus status = ItemStatus.AVAILABLE; // 기본값 설정 // 거래 상태 (판매 중, 예약 중, 거래 완료)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Member seller; // 판매자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category; // 카테고리
    
    @OneToMany(mappedBy = "item", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>(); // 아이템에 대한 리뷰 리스트
    
    @OneToMany(mappedBy = "item", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<ItemImage> images = new ArrayList<>(); // 이미지 리스트

    @Builder
    public Item(String title, Address origin, int price, ItemStatus status, Member seller, Category category) {
        this.title = title;
        this.origin = origin;
        this.price = price;
        this.status = status != null ? status : ItemStatus.AVAILABLE;
        this.seller = seller;
        this.category = category;
    }

    // 상품 정보 업데이트 메서드
    public void updateItem(String title, Address origin, int price, Category category, ItemStatus status) {
        this.title = title;
        this.origin = origin;
        this.price = price;
        this.category = category;

        // 상태값 업데이트
        if (status != null) {
            this.status = status;
        }
    }
}