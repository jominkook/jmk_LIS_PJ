package com.yk.logistic.controller.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.yk.logistic.domain.member.Member;
import com.yk.logistic.repository.member.MemberRepository;
import com.yk.logistic.service.bid.BidService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yk.logistic.dto.item.response.ItemResDto;
import com.yk.logistic.dto.review.response.ReviewResponse;
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
	private final MemberRepository memberRepository;
	private final BidService bidService;

	private static final Logger logger = LoggerFactory.getLogger(ItemViewController.class);


	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("categories", categoryService.findChildCategories());
		return "register-auction";
	}

	@GetMapping
	public String findItemList(Model model, @AuthenticationPrincipal UserDetails userDetails) {
		List<ItemResDto> items = itemService.findAllItems();

		Map<Long, List<ReviewResponse>> itemReviews = new HashMap<>();
		for (ItemResDto item : items) {
			List<ReviewResponse> reviews = reviewService.getReviewsByItemId(item.getId());
			itemReviews.put(item.getId(), reviews);
		}

		// 로그인 사용자의 입찰 경매 ID 목록 추가
		List<Long> myBidAuctionIds = new ArrayList<>();
		if (userDetails != null) {
			String email = userDetails.getUsername();
			Member member = memberRepository.findByEmail(email).orElse(null);
			if (member != null) {
				myBidAuctionIds = bidService.findAuctionIdsByBidder(member.getId());
			}
		}
		model.addAttribute("myBidAuctionIds", myBidAuctionIds);

		model.addAttribute("items", items);
		model.addAttribute("itemReviews", itemReviews);
		logger.info("myBidAuctionIds: {}", myBidAuctionIds);
		return "items";
	}

	@GetMapping("/auctions")
	public String getAuctionItemList(Model model, @AuthenticationPrincipal UserDetails userDetails) {
		List<ItemResDto> auctionItems = itemService.findAuctionItems();
		model.addAttribute("items", auctionItems);

		// 로그인 사용자의 입찰 경매 ID 목록 추가
		List<Long> myBidAuctionIds = new ArrayList<>();
		if (userDetails != null) {
			String email = userDetails.getUsername();
			Member member = memberRepository.findByEmail(email)
					.orElse(null);
			if (member != null) {
				myBidAuctionIds = bidService.findAuctionIdsByBidder(member.getId());
			}
		}
		model.addAttribute("myBidAuctionIds", myBidAuctionIds);
		//logger.info("myBidAuctionIds: {}", myBidAuctionIds);
		return "item";
	}

	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable Long id, Model model) {
		ItemResDto item = itemService.findItem(id);
		if (item == null) {
			throw new IllegalArgumentException("아이템을 찾을 수 없습니다.");
		}
		model.addAttribute("item", item);
		model.addAttribute("categories", categoryService.findChildCategories());
		return "edit-auction";
	}
}