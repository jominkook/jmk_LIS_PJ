package com.yk.logistic.dto.item.request;

import java.util.List;

import com.yk.logistic.domain.address.Address;
import com.yk.logistic.domain.categoryItem.CategoryItem;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveItemReqDto {
	private String name;
	
	private Address origin;
	
	private int price;
	
	private int stockQuantity;
	
	private List<CategoryItem> categories;
}
