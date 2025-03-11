package com.yk.logistic.repository.category;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yk.logistic.domain.category.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}