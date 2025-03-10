package com.yk.logistic.dto.item.response;

import com.yk.logistic.domain.address.Address;

import lombok.Data;

@Data
public class FindItemResDto {
	private String itemName;
	private Address origin;
	private int price;
	private int stockQuantity;
	
}
