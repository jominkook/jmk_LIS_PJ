package com.yk.logistic.controller.item;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yk.logistic.dto.item.response.ItemResDto;
import com.yk.logistic.service.category.CategoryService;
import com.yk.logistic.service.item.ItemService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/view/items")
@RequiredArgsConstructor
public class ItemViewController {
	
	private final ItemService itemService;
	private final CategoryService categoryService;
	
	@GetMapping("/register")
    public String showRegisterForm(Model model) {
		model.addAttribute("categories", categoryService.findChildCategories());
        return "register-item"; // register-item.html 템플릿을 반환합니다.
    }
	 
	@GetMapping
	public String findItemList(Model model) {
	    List<ItemResDto> items = itemService.findAllItems();
	    model.addAttribute("items", items);
	    return "items"; // items.html 템플릿을 반환합니다.
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
