package com.yk.logistic.repository.category;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yk.logistic.domain.category.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByParentIsNotNull(); // 부모가 있는 카테고리만 조회
}