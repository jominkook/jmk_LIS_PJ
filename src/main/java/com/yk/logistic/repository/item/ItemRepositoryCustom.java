package com.yk.logistic.repository.item;

import com.yk.logistic.dto.item.response.ItemResDto;

import java.util.List;

public interface ItemRepositoryCustom {
    List<ItemResDto> findAuctionItems();
}