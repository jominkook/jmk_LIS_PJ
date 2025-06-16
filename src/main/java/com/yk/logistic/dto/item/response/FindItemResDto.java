package com.yk.logistic.dto.item.response;

import com.yk.logistic.domain.address.Address;

import lombok.Data;

@Data
public class FindItemResDto {
    private String itemName; // 상품 이름
    private Address origin; // 상품 위치
    private int price; // 상품 가격
}