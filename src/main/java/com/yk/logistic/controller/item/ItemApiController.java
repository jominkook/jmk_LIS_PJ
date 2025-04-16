package com.yk.logistic.controller.item;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yk.logistic.dto.item.request.SaveItemReqDto;
import com.yk.logistic.dto.item.response.ItemResDto;

import com.yk.logistic.service.item.ItemService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemApiController {
	private final ItemService itemService;
	

	 @PostMapping("/register")
	 public ResponseEntity<ItemResDto> registerItem(@RequestBody SaveItemReqDto reqDto) {
		 //Long currentLoginId = (Long) session.getAttribute(SessionConst.LOGIN_ID); -> Spring Security 구현시 동작안함
	     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	     String email = authentication.getName(); // 인증된 사용자 이메일
	     System.out.println("Authenticated user email: " + email);
	     ItemResDto resDto = itemService.registerItem(reqDto); // memberId는 사용하지 않도록 수정
	     return new ResponseEntity<>(resDto, HttpStatus.OK);
	 } 
	
   
   //수정 요청 처리(JSON)
   @PutMapping("/edit/{id}")
   public ResponseEntity<?> updateItem(@PathVariable Long id, @RequestBody SaveItemReqDto reqDto) {
       itemService.updateItem(id, reqDto); // 아이템 수정
       return ResponseEntity.ok().build(); // 성공 응답 반환
   }
	

}
