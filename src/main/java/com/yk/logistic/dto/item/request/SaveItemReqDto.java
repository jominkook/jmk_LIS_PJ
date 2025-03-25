package com.yk.logistic.dto.item.request;

import com.yk.logistic.domain.address.Address;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveItemReqDto {
    private String name; // 상품 이름
    private Address origin; // 상품 위치 (도로명, 도시, 우편번호)
    private int price; // 상품 가격
    private Long categoryId; // 카테고리 ID
}