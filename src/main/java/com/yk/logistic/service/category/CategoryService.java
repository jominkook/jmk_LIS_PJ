package com.yk.logistic.service.category;

import java.util.List;

import com.yk.logistic.domain.category.Category;

public interface CategoryService {
	List<Category> findChildCategories();
}
