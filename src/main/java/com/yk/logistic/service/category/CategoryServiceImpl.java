package com.yk.logistic.service.category;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yk.logistic.domain.category.Category;
import com.yk.logistic.repository.category.CategoryRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
	
	private final CategoryRepository categoryRepository;

    public List<Category> findChildCategories() {
        return categoryRepository.findByParentIsNotNull(); // 자식 카테고리만 반환
    }

}
