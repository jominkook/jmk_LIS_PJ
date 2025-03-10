package com.yk.logistic.domain.category;

import java.util.ArrayList;
import java.util.List;

import com.yk.logistic.domain.categoryItem.CategoryItem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Category {
	@Id
	@GeneratedValue
	@Column(name = "category_id")
	private Long id;
	
	private String name;
	
	@OneToMany(mappedBy = "category")
	private List<CategoryItem> categoryItems = new ArrayList<>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parrent_id")
	private Category parent;
	
	@OneToMany(mappedBy = "parrent")
	private List<Category> child = new ArrayList<>();
}
