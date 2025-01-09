package com.yk.logistic.service;

import org.springframework.stereotype.Service;

import com.yk.logistic.domain.Item;
import com.yk.logistic.dto.ItemResponse;
import com.yk.logistic.dto.SaveItemRequest;
import com.yk.logistic.repository.JpaItemRepository;
import com.yk.logistic.repository.JpaMemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
	

	//private final JpaMemberRepository memberRepository;
	private final JpaItemRepository itemRepository;
	private final ValidationCheck  validationCheck;
	
	@Override
	public ItemResponse findItem(Long itemId) {
		Item findItem = validationCheck.getItem(itemRepository.findById(itemId));
		
		ItemResponse response = transformDomain(findItem);
		
		return response;
		
	}
	
	//도메인을 DTO로 
	private ItemResponse transformDomain(Item savedItem) {
		ItemResponse response = new ItemResponse();
		
		response.setId(savedItem.getId());
		response.setName(savedItem.getName());
		response.setPrice(savedItem.getPrice());
		response.setStockQuantity(savedItem.getStockQuantity());
		response.setOrigin(savedItem.getOrigin());
		response.setCategories(savedItem.getCategories());
		return response;
	}
	

}
