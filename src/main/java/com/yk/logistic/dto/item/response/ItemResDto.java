package com.yk.logistic.dto.item.response;

import java.util.List;

import com.yk.logistic.domain.address.Address;
import com.yk.logistic.domain.categoryItem.CategoryItem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemResDto {
	private Long id;
	private String name;
	private Address origin;
	private int price;
	private int stockQuantity;
	private List<CategoryItem> categories;

}
