package com.yk.logistic.controller;


import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.yk.logistic.dto.ItemResponse;
import com.yk.logistic.dto.SaveItemRequest;
import com.yk.logistic.service.ItemService;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
	private final ItemService itemService;
	
	
	
	/*@PostMapping("/register")
	public ResponseEntity<ItemResponse> register(@RequestBody SaveItemRequest request ,HttpSession session) {
		Long loginId = (Long) session.getAttribute(SessionConst.LOGIN_ID);
		
		ItemResponse response = itemService.register(request,loginId);
		
		return new ResponseEntity(response, HttpStatus.OK);
		
	}*/
	//타임리프 동작안함 해결해야함
	/*@GetMapping("/{itemId}")
    public String getItem(@PathVariable Long itemId, Model model) {
        ItemResponse item = itemService.findItem(itemId);
        model.addAttribute("item", item);
        model.addAttribute("today", LocalDate.now());
        return "itemDetail"; // itemDetail.html로 이동
    }*/
	
	@GetMapping("/{itemId}")
    public ItemResponse getItem(@PathVariable Long itemId, Model model) {
		return itemService.findItem(itemId);
    }
	
}
