package com.yk.logistic.repository.item;

import java.util.List;

import com.yk.logistic.dto.item.response.FindItemResDto;
public class ItemQueryRepositoryImpl implements ItemQueryRepositroy {

	@Override
	public List<FindItemResDto> findByPriceLessThan(int price) {
		return null;
	}

	@Override
	public List<FindItemResDto> findByItemNameContaining(String itemName) {
		return null;
	}

}
