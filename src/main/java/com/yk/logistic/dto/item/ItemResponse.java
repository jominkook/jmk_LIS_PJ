package com.yk.logistic.dto.item;

import java.util.List;

import com.yk.logistic.domain.address.Address;
import com.yk.logistic.domain.category.CategoryItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemResponse {
	 private Long id;

     private String name;

     private Address origin;

     private int price;

     private int stockQuantity;

     private List<CategoryItem> categories;
     
     //임시
     private Long memberId;
     
}
