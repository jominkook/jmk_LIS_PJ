package com.yk.logistic.service;


import java.util.List;

import com.yk.logistic.dto.ItemResponse;
import com.yk.logistic.dto.SaveItemRequest;

public interface ItemService {
	//재고 등록
	ItemResponse register(SaveItemRequest request,Long memberId);
	
	//재고 아이템 하나 조회
	ItemResponse findItem(Long itemId);
	
	//등록한 아이템 수정
	void updateItem(Long itemId, Long memberId, SaveItemRequest request);
	
	//등록한 아이템 삭제
	void deleteItem(Long itemId, Long memberId);
	
	// 특정 회원의 아이템 리스트 조회
    List<ItemResponse> findItemList(Long memberId);
	


}
