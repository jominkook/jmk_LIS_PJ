package com.yk.logistic.domain.categoryItem;


import com.yk.logistic.domain.category.Category;
import com.yk.logistic.domain.item.Item;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class CategoryItem {
	@Id
	@GeneratedValue
	@Column(name = "category_item")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "category_item_id")
	Category category;
	
	@ManyToOne
	@JoinColumn(name = "item_id")
	Item item;
	

}
