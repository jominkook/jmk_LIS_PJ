package com.yk.logistic.service;

import com.yk.logistic.dto.ItemResponse;
import com.yk.logistic.dto.SaveItemRequest;

public interface ItemService {
	//재고 등록
	ItemResponse registerItem(SaveItemRequest request,Long memberId);
	
	//재고 아이템 하나 조회
	ItemResponse findItem(Long memberId);
	
	//등록한 아이템 수정
	void updateItem(Long itemId, Long memberId, SaveItemRequest request);
	
	//등록한 아이템 삭제
	void deleteItem(Long itemId, Long memberId);

}
