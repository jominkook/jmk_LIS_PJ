package com.yk.logistic.domain.item;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yk.logistic.domain.address.Address;
import com.yk.logistic.domain.categoryItem.CategoryItem;
import com.yk.logistic.domain.member.Member;
import com.yk.logistic.exception.NotEnoughStockException;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
    @Column(name = "item_id")
    private Long id;

    private String name;

    @Embedded
    private Address origin;

    private int price;

    private int stockQuantity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member seller;

    @OneToMany(mappedBy = "item")
    @JsonIgnore // 순환 참조 방지
    private List<CategoryItem> categories = new ArrayList<>();

    @Builder
    public Item(String name, Address origin, int price, int stockQuantity, Member seller) {
        this.name = name;
        this.origin = origin;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.seller = seller;
    }

    public void updateItem(String name, Address origin, int price, int stockQuantity) {
        this.name = name;
        this.origin = origin;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public void addStockCount(int count) {
        stockQuantity += count;
    }

    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("재고량이 부족합니다.");
        }
        this.stockQuantity = restStock;
    }
}
