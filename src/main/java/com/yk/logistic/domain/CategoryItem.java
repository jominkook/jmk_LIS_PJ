package com.yk.logistic.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CategoryItem {
	@Id
	@GeneratedValue
	@Column(name = "category_item_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	Category category;
	
	@ManyToOne
	@JoinColumn(name = "item_id")
	Item item;

}
