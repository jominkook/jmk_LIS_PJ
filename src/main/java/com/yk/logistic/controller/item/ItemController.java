package com.yk.logistic.controller.item;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yk.logistic.constant.SessionConst;
import com.yk.logistic.dto.item.request.SaveItemReqDto;
import com.yk.logistic.dto.item.response.ItemResDto;
import com.yk.logistic.service.item.ItemService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
	private final ItemService itemService;
	
	@PostMapping("/register")
	public ResponseEntity<ItemResDto> registerItem(@RequestBody SaveItemReqDto reqDto,
												   HttpSession session) {
		Long currentLoginId = (Long) session.getAttribute(SessionConst.LOGIN_ID);
		
		ItemResDto resDto = itemService.registerItem(reqDto, currentLoginId);

        return new ResponseEntity(resDto, HttpStatus.OK);
	}

}
