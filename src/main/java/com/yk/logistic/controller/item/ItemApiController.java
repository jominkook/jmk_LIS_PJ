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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yk.logistic.domain.address.Address;
import com.yk.logistic.domain.item.ItemStatus;
import com.yk.logistic.dto.item.request.SaveItemReqDto;
import com.yk.logistic.dto.item.response.ItemResDto;
import com.yk.logistic.service.file.S3FileService;
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
	private final S3FileService s3FileService;
	

	@Operation(summary = "아이템 등록", description = "새로운 아이템을 등록합니다.")
	@PostMapping("/register")
	public ResponseEntity<?> registerItem(
	        @RequestParam("title") String title,
	        @RequestParam("origin") String originJson, // JSON 문자열로 받음
	        @RequestParam("price") int price,
	        @RequestParam("categoryId") Long categoryId,
	        @RequestParam("image") MultipartFile image,
	        @RequestParam(value = "status", required = false) String status) { // 상태값 추가

	    try {
	        // origin JSON 문자열을 객체로 변환
	        ObjectMapper objectMapper = new ObjectMapper();
	        Address origin = objectMapper.readValue(originJson, Address.class);

	        // 파일 업로드 처리
	        String imagePath = s3FileService.uploadFile(image);

	        // 상태값 처리
	        ItemStatus itemStatus = (status != null) ? ItemStatus.valueOf(status) : ItemStatus.AVAILABLE;

	        // 아이템 저장 로직
	        SaveItemReqDto reqDto = new SaveItemReqDto(
	                title,
	                origin.getStreet(),
	                origin.getCity(),
	                origin.getZipCode(),
	                price,
	                categoryId,
	                imagePath,
	                itemStatus.name() // 상태값 추가
	        );
	        ItemResDto resDto = itemService.registerItem(reqDto);

	        return new ResponseEntity<>(resDto, HttpStatus.CREATED);
	    } catch (Exception e) {
	        e.printStackTrace(); // 예외 스택 트레이스 출력
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("아이템 등록 중 오류가 발생했습니다: " + e.getMessage());
	    }
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
