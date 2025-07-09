package com.yk.logistic.service.item;

import java.util.List;

import com.yk.logistic.dto.item.request.SaveItemReqDto;
import com.yk.logistic.dto.item.response.ItemResDto;

public interface ItemService {
    // 아이템 등록
    ItemResDto registerItem(SaveItemReqDto dto);

    // 등록한 아이템 하나 조회
    ItemResDto findItem(Long itemId);

    // 모든 아이템 조회
    List<ItemResDto> findAllItems();

    // 경매 아이템 조회
    List<ItemResDto> findAuctionItems();

    // 등록한 아이템 수정
    void updateItem(Long itemId, SaveItemReqDto dto);

    // 등록한 아이템 삭제
    void deleteItem(Long itemId);
}