package com.yk.logistic.controller.item;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yk.logistic.constant.SessionConst;
import com.yk.logistic.dto.item.request.SaveItemReqDto;
import com.yk.logistic.dto.item.response.ItemResDto;
import com.yk.logistic.service.category.CategoryService;
import com.yk.logistic.service.item.ItemService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
	private final ItemService itemService;
	private final CategoryService categoryService;
	
	 @GetMapping("/register")
     public String showRegisterForm(Model model) {
         model.addAttribute("categories", categoryService.findAllCategories());
         return "register-item"; // register-item.html 템플릿을 반환합니다.
     }

//	 @PostMapping("/register")
//	 public ResponseEntity<ItemResDto> registerItem(@RequestBody SaveItemReqDto reqDto,
//                                                   HttpSession session) {
//        Long currentLoginId = (Long) session.getAttribute(SessionConst.LOGIN_ID);
//        ItemResDto resDto = itemService.registerItem(reqDto, currentLoginId);
//        return new ResponseEntity<>(resDto, HttpStatus.OK);
//    }
	 
	 @PostMapping("/register")
	 public ResponseEntity<ItemResDto> registerItem(@RequestBody SaveItemReqDto reqDto) {
		 //Long currentLoginId = (Long) session.getAttribute(SessionConst.LOGIN_ID); -> Spring Security 구현시 동작안함
	     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	     String email = authentication.getName(); // 인증된 사용자 이메일
	     System.out.println("Authenticated user email: " + email);
	     ItemResDto resDto = itemService.registerItem(reqDto); // memberId는 사용하지 않도록 수정
	     return new ResponseEntity<>(resDto, HttpStatus.OK);
	 } 
	
	@GetMapping("/{itemId}")
	public ResponseEntity<ItemResDto> getItem(@PathVariable Long itemId) {
	
		ItemResDto resDto =  itemService.findItem(itemId);
		return new ResponseEntity<>(resDto,HttpStatus.OK);
	}
	
	@PutMapping("/{itemId}")
    public ResponseEntity<Void> updateItem(@PathVariable Long itemId,
                                           @RequestBody SaveItemReqDto reqDto,
                                           HttpSession session) {
        Long currentLoginId = (Long) session.getAttribute(SessionConst.LOGIN_ID);
        itemService.updateItem(itemId, currentLoginId, reqDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long itemId,
                                           HttpSession session) {
        Long currentLoginId = (Long) session.getAttribute(SessionConst.LOGIN_ID);
        itemService.deleteItem(itemId, currentLoginId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping
    public String findItemList(Model model) {
        List<ItemResDto> items = itemService.findAllItems();
        model.addAttribute("items", items);
        return "items"; // items.html 템플릿을 반환합니다.
    }

}
