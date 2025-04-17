package com.yk.logistic.controller.item;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yk.logistic.dto.item.request.SaveItemReqDto;
import com.yk.logistic.dto.item.response.ItemResDto;

import com.yk.logistic.service.item.ItemService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
@Tag(name = "Item API", description = "아이템 관리 API")
public class ItemApiController {
	private final ItemService itemService;
	

	@Operation(summary = "아이템 등록", description = "새로운 아이템을 등록합니다.")
    @PostMapping("/register")
    public ResponseEntity<ItemResDto> registerItem(@RequestBody SaveItemReqDto reqDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // 인증된 사용자 이메일
        System.out.println("Authenticated user email: " + email);
        ItemResDto resDto = itemService.registerItem(reqDto); // memberId는 사용하지 않도록 수정
        return new ResponseEntity<>(resDto, HttpStatus.OK);
    }

    @Operation(summary = "모든 아이템 조회", description = "모든 아이템 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<ItemResDto>> findItemList() {
        List<ItemResDto> items = itemService.findAllItems();
        return ResponseEntity.ok(items); // JSON 데이터 반환
    }

    @Operation(summary = "아이템 수정", description = "특정 아이템을 수정합니다.")
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> updateItem(@PathVariable Long id, @RequestBody SaveItemReqDto reqDto) {
        itemService.updateItem(id, reqDto); // 아이템 수정
        return ResponseEntity.ok().build(); // 성공 응답 반환
    }
	

}
