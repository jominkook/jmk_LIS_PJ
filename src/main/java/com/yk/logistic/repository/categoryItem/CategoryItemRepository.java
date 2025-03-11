package com.yk.logistic.repository.categoryItem;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yk.logistic.domain.categoryItem.CategoryItem;

public interface CategoryItemRepository extends JpaRepository<CategoryItem, Long> {
}
