package com.yk.logistic.controller.item;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yk.logistic.dto.item.response.ItemResDto;
import com.yk.logistic.dto.review.ReviewResponse;
import com.yk.logistic.service.category.CategoryService;
import com.yk.logistic.service.item.ItemService;
import com.yk.logistic.service.review.ReviewService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/view/items")
@RequiredArgsConstructor
public class ItemViewController {
	
	private final ItemService itemService;
	private final CategoryService categoryService;
	private final ReviewService reviewService;

	
	@GetMapping("/register")
    public String showRegisterForm(Model model) {
		model.addAttribute("categories", categoryService.findChildCategories());
        return "register-item"; // register-item.html 템플릿을 반환.
    }
	
	@GetMapping
	public String findItemList(Model model) {
		// 아이템 목록 가져오기
	    List<ItemResDto> items = itemService.findAllItems();

	    // 각 아이템별 리뷰를 조회하여 모델에 추가
	    Map<Long, List<ReviewResponse>> itemReviews = new HashMap<>();
	    for (ItemResDto item : items) {
	        List<ReviewResponse> reviews = reviewService.getReviewsByItemId(item.getId());
	        itemReviews.put(item.getId(), reviews);
	    }

	    model.addAttribute("items", items); // 아이템 목록
	    model.addAttribute("itemReviews", itemReviews); // 아이템별 리뷰 데이터
	    return "items"; // items.html 템플릿 반환
	}
	 
	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable Long id, Model model) {
	    ItemResDto item = itemService.findItem(id); // 수정할 아이템 정보 가져오기
	    if (item == null) {
	          throw new IllegalArgumentException("아이템을 찾을 수 없습니다.");
	    }
	    model.addAttribute("item", item);
	    model.addAttribute("categories", categoryService.findChildCategories()); // 카테고리 목록
	    return "edit-item"; // edit-item.html 템플릿 반환
    }

}
