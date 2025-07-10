package com.yk.logistic.controller.item;

import java.security.Principal;
import java.util.List;

import com.yk.logistic.domain.member.Member;
import com.yk.logistic.dto.item.request.UpdateItemReqDto;
import com.yk.logistic.service.member.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yk.logistic.domain.address.Address;
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
@Tag(name = "Item API", description = "경매 아이템 관리 API")
public class ItemApiController {
	private final ItemService itemService;
	private final S3FileService s3FileService;
	private final MemberService memberService;

	@Operation(summary = "경매 아이템 등록", description = "새로운 경매 아이템을 등록합니다.")
	@PostMapping("/register")
	public ResponseEntity<ItemResDto> registerItem(
			@RequestParam("title") String title,
			@RequestParam("origin") String originJson,
			@RequestParam("price") int price,
			@RequestParam("categoryId") Long categoryId,
			@RequestParam("image") MultipartFile image,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "startPrice", required = false) Integer startPrice,
			@RequestParam(value = "auctionEndTime", required = false) String auctionEndTime,
			@RequestParam("latitude") Double latitude,
			@RequestParam("longitude") Double longitude,
			@RequestParam(value = "description", required = false) String description,
			Principal principal // 로그인 사용자 정보
	) {
		try {
			// S3에 이미지 업로드
			String imagePath = s3FileService.uploadFile(image);

			// Address 객체 생성
			ObjectMapper objectMapper = new ObjectMapper();
			Address origin = objectMapper.readValue(originJson, Address.class);

			// 빈값 처리
			if (status == null || status.isBlank()) status = "ACTIVE";
			if (description == null) description = "";

			// 현재 로그인 사용자로 seller 지정
			Member seller = memberService.findByEmail(principal.getName());

			// SaveItemReqDto 생성
			SaveItemReqDto reqDto = new SaveItemReqDto(
					title,
					origin.getStreet(),
					origin.getCity(),
					origin.getZipCode(),
					price,
					categoryId,
					imagePath,
					status,
					startPrice,
					auctionEndTime,
					latitude,
					longitude
			);
			reqDto.setDescription(description);
			reqDto.setSeller(seller);

			// 아이템 등록
			ItemResDto resDto = itemService.registerItem(reqDto);
			return new ResponseEntity<>(resDto, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(null);
		}

	}

	@Operation(summary = "경매 아이템 조회", description = "경매에 등록된 아이템 목록을 조회합니다.")
	@GetMapping
	public ResponseEntity<List<ItemResDto>> findAuctionItemList() {
		List<ItemResDto> auctionItems = itemService.findAuctionItems();
		return ResponseEntity.ok(auctionItems);
	}

	@Operation(summary = "경매 아이템 수정", description = "특정 경매 아이템을 수정합니다.")
	@PutMapping("/edit/{id}")
	public ResponseEntity<?> updateItem(
			@PathVariable Long id,
			@RequestParam("title") String title,
			@RequestParam("origin") String originJson,
			@RequestParam("price") int price,
			@RequestParam("categoryId") Long categoryId,
			@RequestParam(value = "images", required = false) List<MultipartFile> images,
			@RequestParam(value = "latitude", required = false) Double latitude,
			@RequestParam(value = "longitude", required = false) Double longitude,
			@RequestParam(value = "description", required = false) String description
	) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Address origin = objectMapper.readValue(originJson, Address.class);

			if (description == null) description = "";

			UpdateItemReqDto reqDto = new UpdateItemReqDto(
					title,
					origin.getStreet(),
					origin.getCity(),
					origin.getZipCode(),
					price,
					categoryId,
					latitude,
					longitude,
					description,
					images
			);
			itemService.updateItem(id, reqDto);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("아이템 수정 중 오류가 발생했습니다: " + e.getMessage());
		}
	}

	@Operation(summary = "경매 아이템 삭제", description = "특정 경매 아이템을 삭제합니다.")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteItem(@PathVariable Long id, Principal principal) {
		itemService.deleteItem(id, principal.getName());
		return ResponseEntity.ok().build();
	}
}