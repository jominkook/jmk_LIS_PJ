package com.yk.logistic.dto;

import java.util.List;

import com.yk.logistic.domain.Address;
import com.yk.logistic.domain.CategoryItem;
import lombok.Getter;
import lombok.Setter;

// Request ItemDto
@Getter
@Setter
public class SaveItemRequest {
	
	private String name;
	
	private Address origin;
	
	private int price;
	
	private int stockQuantity;
	
	private List<CategoryItem> categories;
	

}
