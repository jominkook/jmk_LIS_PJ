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
	
	public ItemResDto(Long id, String name, Address origin, int price, int stockQuantity, List<CategoryItem> categories) {
        this.id = id;
        this.name = name;
        this.origin = origin;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.categories = categories;
    }
	
}
