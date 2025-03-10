package com.yk.logistic.repository.item;

import java.util.List;

import com.yk.logistic.dto.item.response.FindItemResDto;

public interface ItemQueryRepositroy {
	List<FindItemResDto> findByPriceLessThan(int price);
	List<FindItemResDto> findByItemNameContaining(String itemName);
}
